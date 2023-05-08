package com.ripplearc.heavy.radio.parser.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Request(
	@SerializedName("current")
	val current: Boolean?,
	@SerializedName("toggle")
	val toggle: Boolean?,
	@SerializedName("activity")
	val activity: String?
)

data class Message(
	val udid: String,
	val timestamp: Date,
	val requests: Map<RequestType, Request>
)
