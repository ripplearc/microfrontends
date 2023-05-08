package com.ripplearc.heavy.radio.messaging.session

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.*
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.ripplearc.heavy.radio.certificate.KeyStoreFactory
import com.jakewharton.rx.replayingShare
import com.orhanobut.logger.Logger
import com.ripplearc.heavy.toolbelt.aws.initSingle
import com.ripplearc.heavy.toolbelt.constants.Emoji
import com.ripplearc.heavy.toolbelt.rx.SchedulerFactory
import io.reactivex.Observable


/**
 * The Class HttpsSession establish connection with the MQTT using HTTPS.
 * The HTTPS connection requires Keys and Certificates being generated
 * on the AWS IOT and saved locally, which is handled by the KeyStoreFactory.
 * The AWSIotMqttManager must be initialized in the approach of HTTPS as well,
 * which means it needs to ping the server every a few seconds.
 */
internal class HttpsSession constructor(
    private val context: Context,
    private val mobileClient: AWSMobileClient,
    mqttManagerFactory: MqttManagerFactory,
    private val keyStoreFactory: KeyStoreFactory,
    private val awsConfiguration: AWSConfiguration,
    private val schedulerFactory: SchedulerFactory,
    private val reconnectCondition: Observable<Unit>
) : MqttSession {


    override val sessionState: SessionState
        get() = state

    override var mqttManager = mqttManagerFactory.getMqttManager(context, MqttType.Https)

    private var state: SessionState = SessionState.Initiating

    var minWaitTimeOfRetry: Double = 1.5
    var maxWaitTimeOfRetry: Double = 60.0

    /**
     * An observable that emits the Mqtt connection status.
     * After initializing the MobileClient, it loads the keys and certificates
     * before trying to connect.
     */
    override val connectionObservable: Observable<AWSIotMqttClientStatus> by lazy {
        mobileClient.initSingle(context, awsConfiguration)
            .flatMapObservable {
                keyStoreFactory.loadKeyStoreSingle()
                    .flatMapObservable { connect() }
            }
            .replayingShare()
            .doOnNext { Logger.t(Emoji.Smile).d(this.toString()) }
            .doOnSubscribe { Logger.t(Emoji.Smile).d(this.toString()) }
    }

    private fun connect(): Observable<AWSIotMqttClientStatus> =
        mqttManager
            .connectionStatusObservable(
                mobileClient,
                unsubscribeScheduler = schedulerFactory.makeIOScheduler()
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
                state = when (status) {
                    Connected -> SessionState.Connected
                    Connecting, Reconnecting -> SessionState.Connecting
                    ConnectionLost -> SessionState.Suspended
                }
            }
            .doOnError { state = SessionState.Suspended }
            .observeOn(schedulerFactory.makeComputationScheduler())

}

