package com.ripplearc.heavy.toolbelt.model

import java.util.*

data class BatteryStatus(
    val capacity: Int,
    val isCharging: Boolean,
    val plugin: Boolean,
    val temperature: Int,
    val heating: Boolean?,
    val timestamp: Date
) {
    companion object {
        fun setHeating(status: BatteryStatus, heating: Boolean): BatteryStatus =
            BatteryStatus(
                status.capacity,
                status.isCharging,
                status.plugin,
                status.temperature,
                heating,
                status.timestamp
            )
    }
}
