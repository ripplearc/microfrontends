package com.ripplearc.heavy.iot.test.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jakewharton.rxrelay2.ReplayRelay
import com.ripplearc.heavy.common.rxUtil.*
import com.ripplearc.heavy.common.util.date.DateProvider
import com.ripplearc.heavy.data.*
import com.ripplearc.heavy.iot.test.di.IotTestScope
import com.ripplearc.heavy.radio.messaging.MessagingJob
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables.combineLatest
import io.reactivex.rxkotlin.withLatestFrom
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

@IotTestScope
class RequestViewModel @Inject constructor(
    private val context: Context,
    rxPreference: RxCommonPreference,
    @param:Named("Pretty") private val gson: Gson,
    private val messagingJob: MessagingJob,
    private val schedulerFactory: SchedulerFactory,
    private val dateProvider: DateProvider
) : ViewModel() {

    val publishTopicRelay: ReplayRelay<String> = ReplayRelay.create()
    val messageRelay: ReplayRelay<String> = ReplayRelay.create()

    val topicObservable: Observable<String> =
        rxPreference.getObserve(SharedPreferenceKey.SelectedDevice, "")
            .mapNotNull {
                gson.fromJson(it, DeviceModel::class.java)
                    ?.let { model -> "iot/topic/${model.udid}" }
            }

    val messageObservable: Observable<String> =
        combineLatest(
            rxPreference.getObserve(SharedPreferenceKey.SelectedDevice, "")
                .mapNotNull { gson.fromJson(it, DeviceModel::class.java) },
            Observable.interval(0, 5, TimeUnit.SECONDS)
        )
            .mapNotNull { (model, _) ->
                gson.toJson(makeRequestModel(model))
            }

    private fun makeRequestModel(model: DeviceModel): IotRequestModel {
        return IotRequestModel(
            model.udid,
            timestamp = dateProvider.date,
            requests = mapOf(
                RequestType.CheckBatteryStatus to RequestDetail(true),
                RequestType.CheckLocation to RequestDetail(true),
                RequestType.TakePhoto to RequestDetail(true)
            )
        )
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

    fun publishTopic(): Completable =
        publishTopicRelay
            .withLatestFrom(messageObservable)
            .observeOn(schedulerFactory.io())
            .flatMapCompletable { (topic: String, message: String) ->
                messagingJob.publishCompletable(topic, message)
                    .observeOn(schedulerFactory.main())
                    .doOnError {
                        Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                    .onErrorComplete()
            }

    private val connectivity = connectivityObservable(context)
        .filter { it == NetworkState.CONNECTED }
        .map { Unit }
}
