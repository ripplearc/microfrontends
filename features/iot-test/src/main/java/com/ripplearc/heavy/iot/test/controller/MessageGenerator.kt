package com.ripplearc.heavy.test.controller

import android.app.DownloadManager
import com.google.gson.Gson
import com.ripplearc.heavy.common.util.date.DateProvider
import com.ripplearc.heavy.common.data.DeviceModel
import com.ripplearc.heavy.common.data.IotRequestModel
import com.ripplearc.heavy.common.data.RequestDetail
import com.ripplearc.heavy.common.data.RequestType
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
	fun makeRequestModel(device: String, topics: List<RequestType>, toggle: Boolean): String? =
		gson.fromJson(device, DeviceModel::class.java)
			?.let {
				IotRequestModel(
					it.udid,
					timestamp = dateProvider.date,
					requests = topics.map { topic ->
						when (topic) {
							RequestType.ToggleCollectSensorData -> topic to RequestDetail(
								null,
								toggle,
								activity = "Digging"
							)
							else -> topic to RequestDetail(true, null, null)
						}
					}.toMap()
				)
			}?.let {
				gson.toJson(it)
			}
}
