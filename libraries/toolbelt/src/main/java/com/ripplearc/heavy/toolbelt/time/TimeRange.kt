package com.ripplearc.heavy.toolbelt.time

data class TimeRange(
    val startTime: Long,
    val endTime: Long
)

val TimeRange.isValid: Boolean
    get() = startTime > 0 && endTime > 0 && endTime >= startTime

val TimeRange.isNotValid: Boolean
    get() = !isValid
