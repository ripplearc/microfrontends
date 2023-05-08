package com.ripplearc.heavy.radio.parser.model

import com.google.gson.annotations.SerializedName

enum class RequestType {
    @SerializedName("check_location")
    CheckLocation,
    @SerializedName("check_battery_status")
    CheckBatteryStatus,
    @SerializedName("take_photo")
    TakePhoto,
	@SerializedName("toggle_collect_sensor_data")
	ToggleCollectSensorData

}
