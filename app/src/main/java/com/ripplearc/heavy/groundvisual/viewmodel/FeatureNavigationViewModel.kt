package com.ripplearc.heavy.groundvisual.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ripplearc.heavy.common.core.model.Feature
import com.ripplearc.heavy.common.features.*
import com.ripplearc.heavy.features.profile
import com.ripplearc.heavy.groundvisual.appComponent
import javax.inject.Inject

open class FeatureNavigationViewModel : ViewModel() {

    private lateinit var featureManager: FeatureManager

    @Inject
    fun injectMembers(
        featureManager: FeatureManager
    ) {
        this.featureManager = featureManager
    }

    fun getFeature(featureId: String): Feature<*>? =
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