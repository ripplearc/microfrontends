package com.ripplearc.heavy.radio.messaging

import android.content.Context
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos.QOS0
import com.ripplearc.heavy.radio.R
import com.orhanobut.logger.Logger
import com.ripplearc.heavy.toolbelt.constants.Emoji
import com.ripplearc.heavy.toolbelt.rx.onSafeError
import com.ripplearc.heavy.toolbelt.rx.onSafeNext
import dagger.Reusable
import io.reactivex.Observable
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import javax.inject.Inject

/**
 * The Class MessagingTopic subscribe or publish to topics after a connection is established.
 */
@Reusable
internal class MessagingTopic @Inject constructor(private val context: Context) {
    /**
     * Use the mqttManager and subscribes to the given topics with the specified QoS.
     * @param mqttManager The Iot Mqtt Manager that establishes the connection through either HTTPS or WebSocket.
     * @param topics The list of topics to be subscribed.
     * @return An observable that emits received messages on the list of topics.
     */
    fun listenToTopicsObservable(
        mqttManager: AWSIotMqttManager,
        topics: List<String>,
        qos: AWSIotMqttQos = QOS0
    ): Observable<String> =
        Observable.fromIterable(topics)
            .flatMap { listenToSingleTopicObservable(mqttManager, it, qos) }

    private fun listenToSingleTopicObservable(
        mqttManager: AWSIotMqttManager,
        topic: String,
        qos: AWSIotMqttQos = QOS0
    ): Observable<String> =
        Observable.create<String> { emitter ->
            mqttManager.subscribeToTopic(topic, qos) { _, data ->
                data?.let {
                    try {
                        val message = String(data, StandardCharsets.UTF_8)
                        emitter.onSafeNext(message)
                    } catch (e: UnsupportedEncodingException) {
                        emitter.onSafeError(e)
                    }
                }
            }

            emitter.setCancellable {
                try {
                    mqttManager.unsubscribeTopic(topic)
                } catch (e: Exception) {
                }
            }
        }

    fun publish(
        mqttManager: AWSIotMqttManager,
        topic: String,
        message: String,
        qos: AWSIotMqttQos = QOS0
    ) {
        try {
            mqttManager.publishString(message, topic, qos)
        } catch (e: Exception) {
            Logger.t(context.getString(R.string.service_radio))
                .e(Emoji.NoEntry + e.localizedMessage)
        }
    }

}
