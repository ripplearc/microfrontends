package com.ripplearc.heavy.radio.messaging.session

import android.content.Context
import com.amazonaws.mobileconnectors.iot.AWSIotMqttLastWillAndTestament
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.ripplearc.heavy.radio.R
import dagger.Reusable
import java.util.*
import javax.inject.Inject

enum class MqttType {
    Https,
    WebSocket
}

/**
 * The factory that gives the IotMqttManager.
 */
@Reusable
internal class MqttManagerFactory @Inject constructor(
) {
    /**
     * Creates a IotMqttManager based on the type.
     */
    fun getMqttManager(context: Context, type: MqttType): AWSIotMqttManager =
        when (type) {
            MqttType.Https ->
                AWSIotMqttManager(
                    UUID.randomUUID().toString(),
                    context.getString(R.string.iot_endpoint)
                )
            MqttType.WebSocket ->
                AWSIotMqttManager(
                    UUID.randomUUID().toString(),
                    context.getString(R.string.iot_endpoint)
                ).apply {
                    keepAlive = 10
                    mqttLastWillAndTestament = AWSIotMqttLastWillAndTestament(
                        context.getString(R.string.mqtt_lastwill_topic),
                        context.getString(R.string.mqtt_lastwill_message), AWSIotMqttQos.QOS0
                    )
                }
        }

}