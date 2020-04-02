package com.ripplearc.heavy.test.controller

import com.ripplearc.heavy.common.rxUtil.*
import com.ripplearc.heavy.common.data.RequestType
import com.ripplearc.heavy.common.data.SharedPreferenceKey
import com.ripplearc.heavy.radio.messaging.MessagingJob
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables.combineLatest
import io.reactivex.rxkotlin.Observables.zip
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Send message on certain topics.
 *
 * @property rxPreference observe selected devices
 * @property topicGenerator generate sent topics based on selected devices
 * @property messageGenerator generate message to be sent to devices
 * @property messagingJob Sending messages Job
 * @property schedulerFactory factory generating rx scheduler
 */
internal class MessageSender @Inject constructor(
	private val rxPreference: RxCommonPreference,
	private val topicGenerator: TopicGenerator,
	private val messageGenerator: MessageGenerator,
	private val messagingJob: MessagingJob,
	private val schedulerFactory: SchedulerFactory
) {
	val sentTopicObservable: Observable<String>
		get() = rxPreference.getObserve(SharedPreferenceKey.SelectedDevice, "")
			.distinctUntilChanged()
			.mapNotNull(topicGenerator::sentTopic)

	fun sentMessageObservable(requests: List<RequestType>): Observable<String> =
		combineLatest(
			rxPreference.getObserve(SharedPreferenceKey.SelectedDevice, ""),
			Observable.interval(0, 5, TimeUnit.SECONDS)
		).mapNotNull { (model, _) ->
			messageGenerator.makeRequestModel(model, requests)
		}

	fun publishTopic(requests: List<RequestType>): Completable =
		zip(sentMessageObservable(requests), sentTopicObservable)
			.take(1)
			.observeOn(schedulerFactory.io())
			.flatMapCompletable { (message: String, topic: String) ->
				messagingJob.publishCompletable(topic, message)
			}

}
