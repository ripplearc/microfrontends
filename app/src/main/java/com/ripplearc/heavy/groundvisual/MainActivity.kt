package com.ripplearc.heavy.groundvisual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ripplearc.heavy.common_features.IotRosterFeature
import java.lang.Exception
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var strings: Set<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(this)

       if (!appComponent.getFeatureMap().containsKey(IotRosterFeature::class.java)) {
            throw Exception()
        }
    }
}
