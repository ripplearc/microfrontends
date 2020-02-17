package com.ripplearc.heavy.iot.roster.ui

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.ripplearc.heavy.common.toolbox.Emoji
import com.ripplearc.heavy.common.toolbox.log
import com.ripplearc.heavy.iot.roster.R
import com.ripplearc.heavy.iot.roster.di.IotRosterScope
import com.ripplearc.heavy.roster.service.DeviceRosterService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@IotRosterScope
class RosterSpinnerViewModel @Inject constructor(
    context: Context,
    deviceRosterService: DeviceRosterService
) : ViewModel() {
    val spinnerAdapter: ArrayAdapter<String> =
        ArrayAdapter(context, R.layout.spinner_item, ArrayList<String>())
            .also { adapter ->
                deviceRosterService.getDeviceRoster()
                    .subscribeOn(Schedulers.io())
                    .log(Emoji.Smile)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onSuccess = { roster ->
                        roster?.let {
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
                    })
                adapter.setDropDownViewResource(R.layout.spinner_item)
            }
}
