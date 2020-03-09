package com.ripplearc.heavy.data

import java.util.*

data class RequestDetail(val current: Boolean)

data class IotRequestModel(
    val udid: String,
    val timestamp: Date,
    val requests: Map<RequestType, RequestDetail>
)