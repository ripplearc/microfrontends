package com.ripplearc.heavy.test.controller

import android.app.DownloadManager
import com.google.gson.Gson
import com.ripplearc.heavy.common.util.date.DateProvider
import com.ripplearc.heavy.common.data.DeviceModel
import com.ripplearc.heavy.common.data.IotRequestModel
import com.ripplearc.heavy.common.data.RequestDetail
import com.ripplearc.heavy.common.data.RequestType
import com.ripplearc.heavy.iot.test.model.RecordingActivityType
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Named

/**
 * Generate messages to be sent to devices
 *
 * @property gson Gson helper tool generate Json strong from models
 * @property dateProvider
 */
@Reusable
internal class MessageGenerator @Inject constructor(
	@param:Named("Pretty") private val gson: Gson,
	private val dateProvider: DateProvider
) {
	fun makeRequestModel(
		device: String,
		topics: List<RequestType>,
		toggle: Boolean,
		recordingActivityType: RecordingActivityType
	): String? =
		gson.fromJson(device, DeviceModel::class.java)
			?.let {
				IotRequestModel(
					it.udid,
					timestamp = dateProvider.date,
					requests = makeRequestDetails(topics, toggle, recordingActivityType)
				)
			}?.let {
				gson.toJson(it)
			}

	private fun makeRequestDetails(
		topics: List<RequestType>,
		toggle: Boolean,
		recordingActivityType: RecordingActivityType
	): Map<RequestType, RequestDetail> =
		topics.map { topic ->
			when (topic) {
				RequestType.ToggleCollectSensorData -> topic to RequestDetail(
					null,
					toggle,
					activity = recordingActivityType.toString()
				)
				else -> topic to RequestDetail(true, null, null)
			}
		}.toMap()
}
