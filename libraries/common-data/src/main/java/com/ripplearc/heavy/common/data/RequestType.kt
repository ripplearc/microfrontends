package com.ripplearc.heavy.common.data

import com.google.gson.annotations.SerializedName

/**
 * enum of request sent to the device to query status
 */
enum class RequestType {
    @SerializedName("check_location")
    CheckLocation,

    @SerializedName("check_battery_status")
    CheckBatteryStatus,

    @SerializedName("take_photo")
    TakePhoto
}
