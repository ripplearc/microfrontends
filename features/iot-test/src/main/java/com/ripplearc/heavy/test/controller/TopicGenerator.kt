package com.ripplearc.heavy.test.controller

import com.google.gson.Gson
import com.ripplearc.heavy.data.DeviceModel
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Named

/**
 * Generate sent or received topics
 *
 * @property prettyGson parse json string to device model
 */
@Reusable
internal class TopicGenerator @Inject constructor(
    @param:Named("Pretty") private val prettyGson: Gson
) {
    fun receivedTopics(device: String) =
        prettyGson.fromJson(device, DeviceModel::class.java)
            ?.let { model -> listOf("iot/response/battery/${model.udid}") }

    fun sentTopic(device: String) =
        prettyGson.fromJson(device, DeviceModel::class.java)
            ?.let { model -> "iot/topic/${model.udid}" }
}