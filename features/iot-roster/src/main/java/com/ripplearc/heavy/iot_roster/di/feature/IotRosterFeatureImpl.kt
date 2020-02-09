package com.ripplearc.heavy.iot_roster.di.feature

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.ripplearc.heavy.common_core.model.Feature
import com.ripplearc.heavy.common_core.model.FeatureProvider
import com.ripplearc.heavy.common_features.IotRosterFeature
import com.ripplearc.heavy.iot_roster.di.di.DaggerIotRosterComponent
import com.ripplearc.heavy.iot_roster.di.di.IotRosterComponent

internal lateinit var iotRosterComponent: IotRosterComponent

class IotRosterFeatureImpl : IotRosterFeature {
    override fun getMainEntry(): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getShortcutIntent(context: Context): Intent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object : FeatureProvider<IotRosterFeature.Dependencies> {
        override fun get(dependencies: IotRosterFeature.Dependencies): Feature<IotRosterFeature.Dependencies> =
            if (::iotRosterComponent.isInitialized)
                iotRosterComponent.getIotRosterFeature()
            else
                DaggerIotRosterComponent.factory()
                    .create(dependencies, IotRosterFeatureImpl())
                    .also {
                        iotRosterComponent = it
                    }.getIotRosterFeature()
    }
}

