package com.ripplearc.heavy.test.controller

import com.google.gson.Gson
import com.ripplearc.heavy.common.util.date.DateProvider
import com.ripplearc.heavy.data.DeviceModel
import com.ripplearc.heavy.data.IotRequestModel
import com.ripplearc.heavy.data.RequestDetail
import com.ripplearc.heavy.data.RequestType
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Named

@Reusable
internal class MessageGenerator @Inject constructor(
    @param:Named("Pretty") private val gson: Gson,
    private val dateProvider: DateProvider
) {
    fun makeRequestModel(device: String): String? =
        gson.fromJson(device, DeviceModel::class.java)
            ?.let {
                IotRequestModel(
                    it.udid,
                    timestamp = dateProvider.date,
                    requests = mapOf(
                        RequestType.CheckBatteryStatus to RequestDetail(true),
                        RequestType.CheckLocation to RequestDetail(true),
                        RequestType.TakePhoto to RequestDetail(true)
                    )
                )
            }?.let {
                gson.toJson(it)
            }
}