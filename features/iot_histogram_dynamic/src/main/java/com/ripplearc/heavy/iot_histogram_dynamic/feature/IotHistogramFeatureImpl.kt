package com.ripplearc.heavy.iot_histogram_dynamic.feature

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.ripplearc.heavy.common_core.model.Feature
import com.ripplearc.heavy.common_core.model.FeatureProvider
import com.ripplearc.heavy.common_features.IotHistogramFeature
import com.ripplearc.heavy.iot_histogram_dynamic.di.DaggerIotHistogramComponent
import com.ripplearc.heavy.iot_histogram_dynamic.di.IotHistogramComponent

internal lateinit var iotHistogramComponent: IotHistogramComponent

class IotHistogramFeatureImpl : IotHistogramFeature {
    override fun getMainEntry(): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getShortcutIntent(context: Context): Intent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object : FeatureProvider<IotHistogramFeature.Dependencies> {
        override fun get(dependencies: IotHistogramFeature.Dependencies): Feature<IotHistogramFeature.Dependencies> =
            if (::iotHistogramComponent.isInitialized)
                iotHistogramComponent.getIotHistogramFeature()
            else
                DaggerIotHistogramComponent.factory()
                    .create(dependencies, IotHistogramFeatureImpl())
                    .also {
                        iotHistogramComponent = it
                    }.getIotHistogramFeature()

    }
}