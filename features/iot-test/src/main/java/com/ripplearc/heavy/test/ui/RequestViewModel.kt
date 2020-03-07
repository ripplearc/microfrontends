package com.ripplearc.heavy.iot.test.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jakewharton.rxrelay2.ReplayRelay
import com.ripplearc.heavy.common.toolbox.*
import com.ripplearc.heavy.data.DeviceModel
import com.ripplearc.heavy.data.SharedPreferenceKey
import com.ripplearc.heavy.iot.test.di.IotTestScope
import com.ripplearc.heavy.radio.messaging.MessagingJob
import com.ripplearc.heavy.toolbelt.constants.Emoji
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

@IotTestScope
class RequestViewModel @Inject constructor(
    context: Context,
    rxPreference: RxCommonPreference,
    private val gson: Gson,
    private val messagingJob: MessagingJob
) : ViewModel() {

    val publishTopicRelay: ReplayRelay<String> = ReplayRelay.create()
    val messageRelay: ReplayRelay<String> = ReplayRelay.create()

    val topicObservable: Observable<String> =
        rxPreference.getObserve(SharedPreferenceKey.SelectedDevice, "")
            .log(Emoji.Yoga)
            .mapNotNull {
                gson.fromJson(it, DeviceModel::class.java)
                    ?.let { model -> "iot/topic/${model.udid}" }
            }

    fun listenToMessages(): Flowable<String> =
        topicObservable
            .toFlowable(BackpressureStrategy.LATEST)
            .switchMap { topic ->
                messagingJob
                    .apply { reconnectCondition = connectivity }
                    .startJobFlowable(Observable.just(listOf(topic)))
                    .retry(3)
            }

    fun publishTopic(): Observable<Unit> =
        publishTopicRelay
            .withLatestFrom(messageRelay)
            .flatMapSingle { (topic: String, message: String) ->
                gson.toJson(message)?.let { json ->
                    messagingJob.publishSingle(topic, json)
                }
            }

    private val connectivity = connectivityObservable(context)
        .filter { it == NetworkState.CONNECTED }
        .map { Unit }
}
