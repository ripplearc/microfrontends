package com.ripplearc.heavy.iot.roster.ui

import android.content.Context
import android.widget.AdapterView.INVALID_POSITION
import android.widget.ArrayAdapter
import com.jakewharton.rx.replayingShare
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jakewharton.rxrelay2.ReplayRelay
import com.ripplearc.heavy.common.toolbox.*
import com.ripplearc.heavy.data.DeviceModel
import com.ripplearc.heavy.data.SharedPreferenceKey
import com.ripplearc.heavy.iot.roster.R
import com.ripplearc.heavy.iot.roster.di.IotRosterScope
import com.ripplearc.heavy.roster.service.DeviceRosterService
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


@IotRosterScope
class RosterSpinnerViewModel @Inject constructor(
    context: Context,
    private var coroutinesContext: ExecutorCoroutineDispatcher,
    deviceRosterService: DeviceRosterService,
    private val schedulerFactory: SchedulerFactory,
    private val rxPreference: RxCommonPreference,
    private val gson: Gson
) : ViewModel() {

    private val source = ArrayList<String>()
    private var disposables = CompositeDisposable()

    val switchDeviceRelay: ReplayRelay<Int> = ReplayRelay.create()

    private val deviceRoster: Observable<List<DeviceModel>> by lazy {
        deviceRosterService.getDeviceRoster()
            .subscribeOn(schedulerFactory.io())
            .replayingShare()
    }

    val spinnerAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(context, R.layout.spinner_item, source)
            .also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_item)
                viewModelScope.launch(coroutinesContext) {
                    observeRoster(adapter)
                }
            }
    }

    private fun observeRoster(adapter: ArrayAdapter<String>) {
        deviceRoster
            .map { list ->
                list.map { model ->
                    model.name.removePrefix("Manufacture: ")
                }
            }
            .doOnNext { roster ->
                with(adapter) {
                    clear()
                    addAll(roster)
                }
            }
            .observeOn(schedulerFactory.main())
            .safeSubscribeBy(onNext = {
                adapter.notifyDataSetChanged()
            }).disposedBy(disposables)
    }


    fun switchDevice(): Completable =
        switchDeviceRelay
            .filter { it != INVALID_POSITION }
            .log(Emoji.Busy)
            .switchMapCompletable(::saveSelectedModelToPreference)

    private fun saveSelectedModelToPreference(index: Int): Completable? =
        deviceRoster
            .map { it.getOrNull(index) }
            .observeOn(schedulerFactory.io())
            .flatMapCompletable(::saveModelToPreference)

    private fun saveModelToPreference(it: DeviceModel): Completable =
        rxPreference.setCompletable(
            SharedPreferenceKey.SelectedDevice,
            gson.toJson(it)
        )

    fun selectedDeviceObservable(): Observable<Int> =
        rxPreference.getObserve(SharedPreferenceKey.SelectedDevice, "")
            .log(Emoji.Smile)
            .switchMap(::indexOfSelectedModel)
            .log(Emoji.Broadcast)

    private fun indexOfSelectedModel(it: String): Observable<Int> {
        return deviceRoster.mapNotNull { models ->
            gson.fromJson(it, DeviceModel::class.java)
                ?.let { selectedModel ->
                    models.indexOfFirst {
                        selectedModel == it
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
