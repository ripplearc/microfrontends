package com.ripplearc.heavy.common_features

import com.ripplearc.heavy.common_core.model.*
import com.ripplearc.heavy.common_core.qualifier.ApplicationScope
import java.util.*
import javax.inject.Inject

interface FeatureManager {
    val featureMap: FeatureProviderMap
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Feature<D>, reified D : Dependencies> FeatureManager.getFeature(dependencies: D): T? {
    val staticFeature = (featureMap[T::class.java]?.get()
            as? FeatureProvider<D>)
        ?.get(dependencies = dependencies) as? T

    if (staticFeature != null) return staticFeature

    val serviceIterator = ServiceLoader.load(
        T::class.java,
        T::class.java.classLoader
    ).iterator()

    return if (serviceIterator.hasNext()) {
        serviceIterator.next()
            ?.apply {
                (this as? DynamicFeature<D>)?.inject(dependencies)
            }
    } else {
        null
    }
}

@ApplicationScope
class FeatureManagerImpl @Inject constructor(
    override val featureMap: FeatureProviderMap
) : FeatureManager
