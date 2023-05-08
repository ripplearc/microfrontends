package com.ripplearc.heavy.radio.messaging.session

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import io.reactivex.Observable

/**
 * The interface to be implemented by either the WebSocketSession or the HttpsSession.
 */
internal interface MqttSession {
    val connectionObservable: Observable<AWSIotMqttClientStatus>

    val sessionState: SessionState

    val mqttManager: AWSIotMqttManager
}

internal enum class SessionState {
    Initiating,
    Connecting,
    Connected,
    Suspended,
    Terminated
}