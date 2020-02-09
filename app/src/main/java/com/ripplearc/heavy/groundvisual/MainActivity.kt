package com.ripplearc.heavy.groundvisual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ripplearc.heavy.common_features.FeatureManager
import com.ripplearc.heavy.common_features.FeatureManagerImpl
import com.ripplearc.heavy.common_features.IotRosterFeature
import com.ripplearc.heavy.common_features.getFeature
import java.lang.Exception
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var featureManager: FeatureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(this)

        val fragment =
            featureManager.getFeature<IotRosterFeature, IotRosterFeature.Dependencies>(appComponent)
    }
}
