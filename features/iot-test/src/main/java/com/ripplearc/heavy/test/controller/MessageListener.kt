package com.ripplearc.heavy.test.controller

import android.content.Context
import com.ripplearc.heavy.common.rxUtil.*
import com.ripplearc.heavy.data.SharedPreferenceKey
import com.ripplearc.heavy.radio.messaging.MessagingJob
import com.ripplearc.heavy.test.model.MessageItem
import dagger.Reusable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Listens to incoming messages.
 *
 * @property messagingJob Listening jobs.
 * @param context application context
 * @param rxPreference preference that emits changes
 * @param topicGenerator generate listening topics from selected devices
 */
@Reusable
internal class MessageListener @Inject constructor(
	context: Context,
	rxPreference: RxCommonPreference,
	topicGenerator: TopicGenerator,
	private val messagingJob: MessagingJob
) {

	internal fun listen(): Flowable<List<MessageItem>> =
		receivedTopicObservable
			.toFlowable(BackpressureStrategy.LATEST)
			.switchMap { topic ->
				messagingJob
					.apply { reconnectCondition = connectivity }
					.startJobFlowable(Observable.just(topic))
					.retry(3)
					.map(::MessageItem)
			}
			.scan(emptyList()) { messages, newMessage ->
				listOf(newMessage) + messages
			}

	private val receivedTopicObservable =
		rxPreference.getObserve(SharedPreferenceKey.SelectedDevice, "")
			.distinctUntilChanged()
			.mapNotNull(topicGenerator::receivedTopics)

	private val connectivity = connectivityObservable(context)
		.filter { it == NetworkState.CONNECTED }
		.map { Unit }
}
