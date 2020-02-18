package com.ripplearc.heavy.iot.roster.ui

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.ripplearc.heavy.common.toolbox.SchedulerFactory
import com.ripplearc.heavy.common.toolbox.disposedBy
import com.ripplearc.heavy.data.DeviceModel
import com.ripplearc.heavy.iot.roster.R
import com.ripplearc.heavy.iot.roster.di.IotRosterScope
import com.ripplearc.heavy.roster.service.DeviceRosterService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


@IotRosterScope
class RosterSpinnerViewModel @Inject constructor(
    context: Context,
    deviceRosterService: DeviceRosterService,
    private val schedulerFactory: SchedulerFactory
) : ViewModel() {

    private val source = ArrayList<String>()
    private var disposables = CompositeDisposable()

    val spinnerAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(context, R.layout.spinner_item, source)
            .also { adapter ->
                observeRoster(deviceRosterService, adapter)
                adapter.setDropDownViewResource(R.layout.spinner_item)
            }
    }

    private fun observeRoster(
        deviceRosterService: DeviceRosterService,
        adapter: ArrayAdapter<String>
    ) {
        deviceRosterService.getDeviceRoster()
            .subscribeOn(schedulerFactory.io())
            .observeOn(schedulerFactory.main())
            .subscribeBy(onSuccess = { roster ->
                updateRoster(roster, adapter)
            }).disposedBy(disposables)
    }

    private fun updateRoster(
        roster: List<DeviceModel>?,
        adapter: ArrayAdapter<String>
    ) = roster?.let {
        with(adapter) {
            it.map { model ->
                model.name.removePrefix("Manufacture: ")
            }.run {
                clear()
                addAll(this)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
