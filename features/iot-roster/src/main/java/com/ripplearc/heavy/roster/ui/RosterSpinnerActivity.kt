package com.ripplearc.heavy.roster.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ripplearc.heavy.iot.roster.R
import com.ripplearc.heavy.iot.roster.feature.iotRosterComponent

/**
 * RosterSpinnerActivity is the host for feature fragment when starting
 * the feature without the app.
 */
class RosterSpinnerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roster)

        iotRosterComponent.inject(this)
        if (savedInstanceState == null) {
            iotRosterComponent.getIotRosterFeature().getMainEntry()
                .let {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer, it)
                        .commit()
                }
        }
    }

}
