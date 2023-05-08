package com.ripplearc.heavy.toolbelt.model

import com.ripplearc.heavy.toolbelt.time.toDateString

enum class ActivityType {
	STATIONARY,
	IDLING,
	WORKING;

	override fun toString(): String {
		return when (this) {
			STATIONARY -> "Stationary"
			IDLING -> "Idling"
			WORKING -> "Working"
		}
	}
}

data class ActivityModel(
	val activity: ActivityType,
	val magnitude: FloatArray?,
	val angleChange: FloatArray?,
	val time: Long,
	val arbitration: String
) {
	override fun toString(): String {
		val mag = magnitude?.let { mag ->
			" mag: %.3f, %.3f, %.3f".format(
				mag.getOrNull(0),
				mag.getOrNull(1),
				mag.getOrNull(2)
			)
		}

		val ang = angleChange?.let { angle ->
			" angle: %.3f, %.3f, %.3f ".format(
				angle.getOrNull(0),
				angle.getOrNull(1),
				angle.getOrNull(2)
			)
		} ?: "None"
		return "${time.toDateString}; $mag; $ang; $activity; $arbitration"
	}

	companion object {
		const val csvSchema: String = "time,magnitude,activity"

		fun create(activity: ActivityType) =
			ActivityModel(
				activity,
				floatArrayOf(0F, 0F, 0F),
				floatArrayOf(0F, 0F, 0F),
				0,
				"solo"
			)
	}
}

