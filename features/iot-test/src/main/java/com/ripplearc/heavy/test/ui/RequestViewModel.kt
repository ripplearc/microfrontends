package com.ripplearc.heavy.iot.test.ui

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.ripplearc.heavy.common.toolbox.RxCommonPreference
import com.ripplearc.heavy.common.toolbox.log
import com.ripplearc.heavy.common.toolbox.mapNotNull
import com.ripplearc.heavy.data.DeviceModel
import com.ripplearc.heavy.data.SharedPreferenceKey
import com.ripplearc.heavy.iot.test.di.IotTestScope
import com.ripplearc.heavy.toolbelt.constants.Emoji
import dagger.Lazy
import io.reactivex.Observable
import javax.inject.Inject

@IotTestScope
class RequestViewModel @Inject constructor(
    rxPreference: RxCommonPreference,
    private val gson: Gson
) : ViewModel() {
    val topicObservable: Observable<String> =
        rxPreference.getObserve(SharedPreferenceKey.SelectedDevice, "")
            .log(Emoji.Yoga)
            .mapNotNull {
                gson.fromJson(it, DeviceModel::class.java)
                    ?.let { model -> "iot/topic/${model.udid}" }
            }
}
