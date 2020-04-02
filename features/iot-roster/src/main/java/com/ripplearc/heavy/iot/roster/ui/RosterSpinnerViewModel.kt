package com.ripplearc.heavy.iot.roster.ui

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jakewharton.rx.replayingShare
import com.ripplearc.heavy.common.rxUtil.RxCommonPreference
import com.ripplearc.heavy.common.rxUtil.SchedulerFactory
import com.ripplearc.heavy.common.rxUtil.mapNotNull
import com.ripplearc.heavy.common.data.DeviceModel
import com.ripplearc.heavy.common.data.SharedPreferenceKey
import com.ripplearc.heavy.iot.roster.R
import com.ripplearc.heavy.roster.service.DeviceRosterService
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named


internal class RosterSpinnerViewModel @Inject constructor(
    context: Context,
    deviceRosterService: DeviceRosterService,
    private val schedulerFactory: SchedulerFactory,
    private val rxPreference: RxCommonPreference,
    @param:Named("Pretty") private val gson: Gson
) : ViewModel() {

    private val source = ArrayList<String>()

    private val deviceRoster: Observable<List<DeviceModel>> by lazy {
        deviceRosterService.getDeviceRoster()
            .subscribeOn(schedulerFactory.io())
            .replayingShare()
    }

    val spinnerAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(context, R.layout.spinner_item, source)
            .also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_item)
            }
    }

    fun observeRoster() =
        deviceRoster
            .mapNotNull { list ->
                list.map { model ->
                    model.name.removePrefix("Manufacture: ")
                }
            }

    fun saveSelectedModelToPreference(index: Int): Completable =
        deviceRoster
            .mapNotNull { it.getOrNull(index) }
            .observeOn(schedulerFactory.io())
            .flatMapCompletable(::saveModelToPreference)

    private fun saveModelToPreference(it: DeviceModel): Completable =
        rxPreference.setCompletable(
            SharedPreferenceKey.SelectedDevice,
            gson.toJson(it)
        )

    fun selectedDeviceObservable(): Observable<Int> =
        rxPreference.getObserve(SharedPreferenceKey.SelectedDevice, "")
            .switchMap(::indexOfSelectedModel)

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
}
