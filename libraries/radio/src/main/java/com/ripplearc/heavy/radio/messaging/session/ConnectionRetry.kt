package com.ripplearc.heavy.radio.messaging.session

import android.content.Context
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus
import com.ripplearc.heavy.toolbelt.constants.Emoji
import com.ripplearc.heavy.toolbelt.rx.NetworkState
import com.ripplearc.heavy.toolbelt.rx.connectivityObservable
import com.ripplearc.heavy.toolbelt.rx.enumerated
import com.ripplearc.heavy.toolbelt.rx.log
import io.reactivex.Observable
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttException.REASON_CODE_CONNECTION_LOST
import java.util.concurrent.TimeUnit
import kotlin.math.pow

/**
 * Extension of Observable<AWSIotMqttClientStatus> to retry when losing internet
 * connection or other error. If the error comes from lost of internet connection,
 * then retry after the internet comes back. Otherwise retry with backoff delay.
 * @param minWaitTimeOfRetry The minimum waiting time before retry. Only applies to error other lost of internet connection.
 * @param maxWaitTimeOfRetry  The maximum waiting time before retry. Only applies to error other lost of internet connection.
 */
internal fun Observable<AWSIotMqttClientStatus>.retryOnRestoredConnectivity(
    reconnectCondition: Observable<Unit>,
    minWaitTimeOfRetry: Double = 1.5,
    maxWaitTimeOfRetry: Double = 60.0
): Observable<AWSIotMqttClientStatus> = retryWhen { errors ->
    errors
        .enumerated()
        .switchMap { (attemptCount, error) ->
            if (error is MqttException &&
                intArrayOf(14908, REASON_CODE_CONNECTION_LOST.toInt()).contains(error.reasonCode)
            ) {
                reconnectCondition
            } else {
                val waitTime = minWaitTimeOfRetry
                    .pow(attemptCount.toDouble().coerceAtMost(10.0))
                    .coerceAtMost(maxWaitTimeOfRetry)
                Observable.timer(waitTime.toLong(), TimeUnit.SECONDS)
                    .log(Emoji.HorizontalTrafficLight)
            }
        }
}