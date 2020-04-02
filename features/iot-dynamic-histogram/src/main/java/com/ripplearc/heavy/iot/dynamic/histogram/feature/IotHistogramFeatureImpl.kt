package com.ripplearc.heavy.iot.dynamic.histogram.feature

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.auto.service.AutoService
import com.ripplearc.heavy.features.features.IotHistogramFeature
import com.ripplearc.heavy.histogram.ui.HistogramActivity
import com.ripplearc.heavy.iot.dynamic.histogram.di.DaggerIotHistogramComponent
import com.ripplearc.heavy.iot.dynamic.histogram.di.IotHistogramComponent
import com.ripplearc.heavy.iot.dynamic.histogram.ui.IotHistogramFragment

internal lateinit var iotHistogramComponent: IotHistogramComponent

@AutoService(IotHistogramFeature::class)
class IotHistogramFeatureImpl :
    IotHistogramFeature {
    override fun getMainEntry(): Fragment = IotHistogramFragment.newInstance()

    override fun getShortcutIntent(context: Context): Intent =
        Intent(context, HistogramActivity::class.java)

    override fun inject(dependencies: IotHistogramFeature.Dependencies) {
        if (::iotHistogramComponent.isInitialized)
            return
        else
            DaggerIotHistogramComponent.factory()
                .create(dependencies, IotHistogramFeatureImpl())
                .also { iotHistogramComponent = it }
    }
}