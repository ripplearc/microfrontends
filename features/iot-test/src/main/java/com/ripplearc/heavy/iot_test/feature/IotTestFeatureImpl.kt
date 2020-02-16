package com.ripplearc.heavy.iot_test.feature

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.ripplearc.heavy.common.core.model.Feature
import com.ripplearc.heavy.common.core.model.FeatureProvider
import com.ripplearc.heavy.common.features.IotTestFeature
import com.ripplearc.heavy.iot_test.di.DaggerIotTestComponent
import com.ripplearc.heavy.iot_test.di.IotTestComponent
import com.ripplearc.heavy.iot_test.ui.RequestFragment

internal lateinit var iotTestComponent: IotTestComponent

class IotTestFeatureImpl : IotTestFeature {
    override fun getMainEntry(): Fragment = RequestFragment.newInstance()

    override fun getShortcutIntent(context: Context): Intent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object : FeatureProvider<IotTestFeature.Dependencies> {
        override fun get(dependencies: IotTestFeature.Dependencies): Feature<IotTestFeature.Dependencies> =
            if (::iotTestComponent.isInitialized)
                iotTestComponent.getIotTestFeature()
            else
                DaggerIotTestComponent.factory()
                    .create(dependencies, IotTestFeatureImpl())
                    .also {
                        iotTestComponent = it
                    }.getIotTestFeature()
    }

}