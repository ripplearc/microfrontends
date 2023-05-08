package com.ripplearc.heavy.radio.messaging.session

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.ripplearc.heavy.radio.di.RadioScope
import com.jakewharton.rx.replayingShare
import com.ripplearc.heavy.toolbelt.aws.initSingle
import com.ripplearc.heavy.toolbelt.rx.SchedulerFactory
import io.reactivex.Observable

/**
 * The Class WebSocketSession uses the WebSocket to communicate with the MqttServer.
 * It uses the Certificate of Signature Version 4, which seems to be handled by
 * the AWS SDK itself.
 */
internal class WebSocketSession constructor(
    private val context: Context,
    private val mobileClient: AWSMobileClient,
    mqttManagerFactory: MqttManagerFactory,
    private val awsConfiguration: AWSConfiguration,
    private val schedulerFactory: SchedulerFactory,
    private val reconnectCondition: Observable<Unit>
) : MqttSession {

    var minWaitTimeOfRetry: Double = 1.5
    var maxWaitTimeOfRetry: Double = 60.0

    override val sessionState: SessionState
        get() = state

    override var mqttManager = mqttManagerFactory.getMqttManager(context, MqttType.WebSocket)
    private var state: SessionState = SessionState.Initiating

    /**
     * An observable that emits the connection status with the Mqtt server through WebSocket.
     * It tries to connect after the successful init of the mobile client.
     */
    override val connectionObservable: Observable<AWSIotMqttClientStatus> by lazy {
        mobileClient.initSingle(context, awsConfiguration)
            .flatMapObservable { connect() }
            .replayingShare()
    }

    private fun connect(): Observable<AWSIotMqttClientStatus> =
        mqttManager
            .connectionStatusObservable(
                mobileClient,
                schedulerFactory.makeIOScheduler()
            )
            .recordConnectionStatus()
            .retryOnRestoredConnectivity(
                reconnectCondition,
                minWaitTimeOfRetry,
                maxWaitTimeOfRetry
            )
            .doFinally { state = SessionState.Terminated }

    private fun Observable<AWSIotMqttClientStatus>.recordConnectionStatus() =
        observeOn(schedulerFactory.makeSequentialScheduler())
            .doOnNext { status ->
                when (status) {
                    AWSIotMqttClientStatus.Connected -> state = SessionState.Connected
                    AWSIotMqttClientStatus.Connecting -> state = SessionState.Connecting
                    AWSIotMqttClientStatus.ConnectionLost -> state = SessionState.Suspended
                }
            }
            .doOnError { state = SessionState.Suspended }
            .observeOn(schedulerFactory.makeComputationScheduler())
}
