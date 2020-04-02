package com.ripplearc.heavy.histogram.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ripplearc.heavy.iot.dynamic.histogram.feature.iotHistogramComponent
import com.ripplearc.heavy.iot.test.R

/**
 * HistogramActivity is the host for feature fragment when starting
 * the feature without the app.
 */
class HistogramActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        iotHistogramComponent.inject(this)
        if (savedInstanceState == null) {
            iotHistogramComponent.getIotHistogramFeature().getMainEntry()
                .let {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer, it)
                        .commit()
                }
        }
    }

}
