package com.ripplearc.heavy.radio.messaging.session

import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.orhanobut.logger.Logger
import com.ripplearc.heavy.toolbelt.constants.Emoji
import com.ripplearc.heavy.toolbelt.rx.log
import com.ripplearc.heavy.toolbelt.rx.onSafeError
import com.ripplearc.heavy.toolbelt.rx.onSafeNext
import io.reactivex.Observable
import io.reactivex.Scheduler
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttException.*

/**
 * An observable that emits the connection status with the Mqtt server.
 * It can be used for both the HTTPS and WebSocket.
 */
internal fun AWSIotMqttManager.connectionStatusObservable(
    mobileClient: AWSMobileClient,
    unsubscribeScheduler: Scheduler
): Observable<AWSIotMqttClientStatus> =
    Observable.create<AWSIotMqttClientStatus> { emitter ->
        connect(mobileClient) { status: AWSIotMqttClientStatus?, throwable: Throwable? ->
            throwable?.let {
                if (throwable is MqttException &&
                    intArrayOf(
                        REASON_CODE_CLIENT_CONNECTED.toInt(),
                        REASON_CODE_CONNECT_IN_PROGRESS.toInt()
                    ).contains(throwable.reasonCode)
                ) {
                    Logger.t(Emoji.Busy)
                        .e("Connection already in progress, no interruption. Ignore the event.")
                    return@connect
                } else {
                    emitter.onSafeError(it)
                }
            }

            status?.let {
                if (it == AWSIotMqttClientStatus.ConnectionLost) {
                    emitter.onSafeError(MqttException(REASON_CODE_CONNECTION_LOST.toInt()))
                } else {
                    emitter.onSafeNext(it)
                }
            }
        }

        emitter.setCancellable {
            disconnect()
        }
    }
        .log(Emoji.TrafficLight)
        .unsubscribeOn(unsubscribeScheduler)
