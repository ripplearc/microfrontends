package com.ripplearc.heavy.common_features

import com.ripplearc.heavy.common_core.model.Dependencies
import com.ripplearc.heavy.common_core.model.Feature
import com.ripplearc.heavy.common_core.model.FeatureProvider
import com.ripplearc.heavy.common_core.model.FeatureProviderMap
import com.ripplearc.heavy.common_core.qualifier.ApplicationScope
import javax.inject.Inject

interface FeatureManager {
    val featureMap: FeatureProviderMap
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Feature<D>, D : Dependencies> FeatureManager.getFeature(dependencies: D): T? {
    return (featureMap[T::class.java]?.get()
            as? FeatureProvider<D>)
        ?.get(dependencies = dependencies) as? T
}

@ApplicationScope
class FeatureManagerImpl @Inject constructor(
    override val featureMap: FeatureProviderMap
) : FeatureManager
