package com.ripplearc.heavy.iot.roster.feature

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.ripplearc.heavy.common.core.model.Feature
import com.ripplearc.heavy.common.core.model.FeatureProvider
import com.ripplearc.heavy.common.features.IotRosterFeature
import com.ripplearc.heavy.iot.roster.di.DaggerIotRosterComponent
import com.ripplearc.heavy.iot.roster.di.IotRosterComponent
import com.ripplearc.heavy.iot.roster.ui.RosterSpinnerFragment

internal lateinit var iotRosterComponent: IotRosterComponent

class IotRosterFeatureImpl : IotRosterFeature {
    override fun getMainEntry(): Fragment = RosterSpinnerFragment.newInstance()

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
