package com.ripplearc.heavy.common.data

import com.google.gson.annotations.SerializedName

data class DeviceModel(
    @SerializedName("udid")
    val udid: String,

    @SerializedName("name")
    val name: String
)
