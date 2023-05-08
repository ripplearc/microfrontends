package com.ripplearc.heavy.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ripplearc.heavy.iot.test.R
import com.ripplearc.heavy.iot.test.feature.iotTestComponent

/**
 * RequestActivity is the host for feature fragment when starting
 * the feature without the app.
 */
internal class RequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        iotTestComponent.inject(this)
        if (savedInstanceState == null) {
            iotTestComponent.getIotTestFeature().getMainEntry()
                .let {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer, it)
                        .commit()
                }
        }
    }

}
