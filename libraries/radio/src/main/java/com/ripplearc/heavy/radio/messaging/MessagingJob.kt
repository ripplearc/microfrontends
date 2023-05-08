package com.ripplearc.heavy.radio.messaging

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected
import com.ripplearc.heavy.radio.messaging.session.MqttSession
import com.ripplearc.heavy.radio.messaging.session.MqttType
import com.ripplearc.heavy.radio.messaging.session.SessionFactory
import com.ripplearc.heavy.toolbelt.constants.Emoji
import com.ripplearc.heavy.toolbelt.job.SwitchableJob
import com.ripplearc.heavy.toolbelt.rx.SchedulerFactory
import com.ripplearc.heavy.toolbelt.rx.log
import io.reactivex.*
import javax.inject.Inject
/**
 * The Class MessagingJob starts the connection with the Mqtt server and listens to a list of topics.
 * MqttSession determines whether the connection is through HTTPS or WebSocket.
 */
class MessagingJob @Inject internal constructor(
    private val sessionFactory: SessionFactory,
    private val messagingTopic: MessagingTopic,
    private val schedulerFactory: SchedulerFactory
) : SwitchableJob<List<String>, String> {

    var reconnectCondition: Observable<Unit>? = null

    /**
     * @param switch When the list is empty, then turn off the connection. Otherwise,
     * subscribe to all the topics in the list.
     * @return An observable that emits received messages on the list of topics.
     */
    override fun startJobFlowable(switch: Observable<List<String>>): Flowable<String> =
        switch
            .subscribeOn(schedulerFactory.makeComputationScheduler())
            .switchMap { topics ->
                if (topics.isEmpty())
                    Observable.empty()
                else {
                    session.let {
                        it.connectionObservable
                            .switchMap { status -> listenOnConnected(status, topics, it) }
                    }
                }
            }
            .toFlowable(BackpressureStrategy.LATEST)

    private fun listenOnConnected(
        status: AWSIotMqttClientStatus,
        topics: List<String>,
        session: MqttSession
    ): Observable<String> =
        when (status) {
            Connected -> messagingTopic.listenToTopicsObservable(session.mqttManager, topics)
                .log(Emoji.Mail)
            else -> Observable.empty()
        }

    /**
     * Publish the message as Json on the topic. It first tries to establish the connection
     * and then publish.
     */

    fun publishCompletable(topic: String, message: String): Completable =
        session.let {
            it.connectionObservable
                .switchMapCompletable { status ->
                    if (status == Connected)
                        Completable.fromCallable {
                            messagingTopic.publish(it.mqttManager, topic, message)
                        }
                    else
                        Completable.error(Throwable("Not connected"))
                }
                .log(Emoji.Broadcast)
        }


    private val session: MqttSession
        get() = sessionFactory.getSession(
            MqttType.WebSocket,
            reconnectCondition ?: Observable.just(Unit)
        )
}
