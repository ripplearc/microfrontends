package com.ripplearc.heavy.radio.messaging.session

import android.content.Context
import androidx.core.internal.view.SupportSubMenu
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.ripplearc.heavy.radio.certificate.KeyStoreFactory
import com.ripplearc.heavy.radio.di.RadioScope
import com.ripplearc.heavy.toolbelt.rx.SchedulerFactory
import io.reactivex.Observable
import javax.inject.Inject

/**
 * The class SessionFactory creates a new session when there is none or
 * the current one is in suspended or terminated mode. It also removes any
 * terminated session.
 */
@RadioScope
internal class SessionFactory @Inject constructor(
    private val context: Context,
    private val mobileClient: AWSMobileClient,
    private val mqttManagerFactory: MqttManagerFactory,
    private val keyStoreFactory: KeyStoreFactory,
    private val awsConfiguration: AWSConfiguration,
    private val schedulerFactory: SchedulerFactory
) {

    private val sessions = mutableListOf<MqttSession>()

    /**
     * Get a live session that is either in initiating, connecting or connected state.
     */
    fun getSession(type: MqttType, reconnectCondition: Observable<Unit>): MqttSession {
        removeDeadSession()
        return getLiveSession(type, reconnectCondition)
    }

    private fun getLiveSession(
        type: MqttType,
        reconnectCondition: Observable<Unit>
    ): MqttSession =
        sessions
            .filter {
                (type == MqttType.Https && it is HttpsSession)
                        || (type == MqttType.WebSocket && it is WebSocketSession)
            }
            .firstOrNull {
                !listOf(SessionState.Suspended, SessionState.Terminated)
                    .contains(it.sessionState)
            } ?: getNewSession(type, reconnectCondition)

    private fun removeDeadSession() =
        sessions
            .removeAll {
                listOf(SessionState.Terminated).contains(it.sessionState)
            }

    private fun getNewSession(type: MqttType, reconnectCondition: Observable<Unit>) =
        when (type) {
            MqttType.Https -> HttpsSession(
                context,
                mobileClient,
                mqttManagerFactory,
                keyStoreFactory,
                awsConfiguration,
                schedulerFactory,
                reconnectCondition
            ).also {
                sessions.add(it)
            }

            MqttType.WebSocket -> WebSocketSession(
                context,
                mobileClient,
                mqttManagerFactory,
                awsConfiguration,
                schedulerFactory,
                reconnectCondition
            ).also {
                sessions.add(it)
            }
        }
}