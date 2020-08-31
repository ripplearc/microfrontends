package com.ripplearc.heavy.iot.test.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.jakewharton.rx.replayingShare
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.jakewharton.rxrelay2.ReplayRelay
import com.ripplearc.heavy.common.data.RequestType
import com.ripplearc.heavy.common.rxUtil.Emoji
import com.ripplearc.heavy.common.rxUtil.SchedulerFactory
import com.ripplearc.heavy.common.rxUtil.log
import com.ripplearc.heavy.iot.test.model.RecordingActivityType
import com.ripplearc.heavy.test.controller.MessageListener
import com.ripplearc.heavy.test.controller.MessageSender
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables.combineLatest
import javax.inject.Inject

internal class RequestViewModel @Inject internal constructor(
	private val context: Context,
	private val messageSender: MessageSender,
	private val messageListener: MessageListener,
	private val schedulerFactory: SchedulerFactory
) : ViewModel() {

	val shouldStartRecordingRelay: Relay<Boolean> = ReplayRelay.create()

	val shouldSendRecordingMessageRelay: Relay<Boolean> = ReplayRelay.create()

	val recordingActivityTypeRelay: Relay<RecordingActivityType> = ReplayRelay.create()

	val sentTopicObservable: Observable<String> =
		messageSender.sentTopicObservable

	val sentMessageObservable: Observable<String> =
		combineLatest(
			shouldSendRecordingMessageRelay,
			shouldStartRecordingRelay,
			recordingActivityTypeRelay
		).switchMap { (shouldSendRecordingMessage, shouldStartRecording, recordingActivityType) ->
			val requests = listOf(
				RequestType.CheckBatteryStatus,
				RequestType.CheckLocation
			) + if (shouldSendRecordingMessage) listOf(RequestType.ToggleCollectSensorData) else emptyList()
			messageSender.sentMessageObservable(
				requests,
				shouldStartRecording,
				recordingActivityType
			).replayingShare()
		}

	internal fun listenToMessages() = messageListener.listen()

	fun publishTopic(): Completable =
		messageSender.publishTopic(
			sentMessageObservable.log(Emoji.Heart)
		)
			.observeOn(schedulerFactory.main())
			.doOnError {
				Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
			}
}
