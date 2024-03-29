package com.ripplearc.heavy.groundvisual.viewmodel

import androidx.lifecycle.ViewModel
import com.ripplearc.heavy.common.features.platform.Feature
import com.ripplearc.heavy.common.features.platform.IotHistogramFeature
import com.ripplearc.heavy.common.features.platform.IotRosterFeature
import com.ripplearc.heavy.common.features.platform.IotTestFeature
import com.ripplearc.heavy.common.features.platform.profile
import com.ripplearc.heavy.common.features.manager.FeatureManager
import com.ripplearc.heavy.common.features.manager.getFeature
import com.ripplearc.heavy.groundvisual.appComponent
import javax.inject.Inject

interface FeatureNavigation {
    fun getFeature(featureId: String): Feature<*>?
}

/**
 * FeatureNavigationViewModel is the base Class for navigating to feature page.
 */
class FeatureNavigationViewModel @Inject constructor(private val featureManager: FeatureManager) :
    FeatureNavigation, ViewModel() {

    override fun getFeature(featureId: String): Feature<*>? =
        when (featureId) {
            IotTestFeature::class.profile.id -> {
                featureManager.getFeature<IotTestFeature, IotTestFeature.Dependencies>(dependencies = appComponent)
            }
            IotRosterFeature::class.profile.id -> {
                featureManager.getFeature<IotRosterFeature, IotRosterFeature.Dependencies>(
                    dependencies = appComponent
                )
            }
            IotHistogramFeature::class.profile.id -> {
                featureManager.getFeature<IotHistogramFeature, IotHistogramFeature.Dependencies>(
                    dependencies = appComponent
                )
            }
            else -> null
        }
}
