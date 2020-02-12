package com.ripplearc.heavy.iot_histogram_dynamic.feature

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.auto.service.AutoService
import com.ripplearc.heavy.common_features.IotHistogramFeature
import com.ripplearc.heavy.iot_histogram_dynamic.di.DaggerIotHistogramComponent
import com.ripplearc.heavy.iot_histogram_dynamic.di.IotHistogramComponent
import com.ripplearc.heavy.iot_histogram_dynamic.ui.IotHistogramFragment

internal lateinit var iotHistogramComponent: IotHistogramComponent

@AutoService(IotHistogramFeature::class)
class IotHistogramFeatureImpl : IotHistogramFeature {
    override fun getMainEntry(): Fragment = IotHistogramFragment.newInstance()

    override fun getShortcutIntent(context: Context): Intent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inject(dependencies: IotHistogramFeature.Dependencies) {
        if (::iotHistogramComponent.isInitialized)
            return
        else
            DaggerIotHistogramComponent.factory()
                .create(dependencies, IotHistogramFeatureImpl())
                .also { iotHistogramComponent = it }
    }
}