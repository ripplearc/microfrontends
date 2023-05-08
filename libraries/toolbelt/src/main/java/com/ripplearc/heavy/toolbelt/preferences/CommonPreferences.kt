package com.ripplearc.heavy.toolbelt.preferences

import android.content.Context
import dagger.Reusable
import net.grandcentrix.tray.TrayPreferences
import javax.inject.Inject

@Reusable
class CommonPreferences @Inject constructor(context: Context) :
    TrayPreferences(context, "common", 1) {
    companion object {
        val CAMERA_ON: String
            get() = "camera.on"

        val GPS_ON: String
            get() = "gps.on"

        val ACTIVITY_ON: String
            get() = "activity.on"

		val ACTIVITY_SIMULATION_OPTION: String
			get() = "activity.simulation"

		val ACCELERATION_ON: String
            get() = "sensor.on"

        val PREFERENCE_KEY_SDCARD_ROOT_URI
            get() = "preference.key.root.uri"

        val SAMPLE_START_TIME: String
            get() = "sample.start.time"

        val SAMPLE_END_TIME: String
            get() = "sample.end.time"
    }
}
