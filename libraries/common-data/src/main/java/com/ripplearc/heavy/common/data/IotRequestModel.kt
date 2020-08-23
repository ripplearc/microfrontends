package com.ripplearc.heavy.common.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * The specific requirement for a request.
 *
 * @property current whether query for the realtime value or not
 */
data class RequestDetail(
	val current: Boolean?,
	val toggle: Boolean?,
	val activity: String?
)

/**
 * Data model sent to the device to query status.
 *
 * @property udid identity of the device
 * @property timestamp timestamp when sending the request
 * @property requests set of requests with specific requirement for each request
 */
data class IotRequestModel(
	val udid: String,
	val timestamp: Date,
	val requests: Map<RequestType, RequestDetail>
)
