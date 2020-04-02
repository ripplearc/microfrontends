package com.ripplearc.heavy.common.features.manager

import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.common.features.di.Dependencies
import com.ripplearc.heavy.common.features.platform.DynamicFeature
import com.ripplearc.heavy.common.features.platform.Feature
import java.util.*
import javax.inject.Inject

/**
 * FeatureManager stores the multibindings map for the features, and
 * loads feature implementation.
 */
interface FeatureManager {
    val featureMap: FeatureProviderMap
}

/**
 * Get the implementation of the feature. If it is static feature, get its
 * implementation from the Dagger multi-bindings. If it is dynamic feature, get
 * its implementation from the ServiceLoader.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Feature<D>, reified D : Dependencies> FeatureManager.getFeature(dependencies: D): T? {
    val staticFeature = (featureMap[T::class.java]?.get()
            as? FeatureProvider<D>)
        ?.get(dependencies = dependencies) as? T

    if (staticFeature != null) return staticFeature

    with(loadService<T>()) {
        return if (hasNext()) {
            next()?.apply { (this as? DynamicFeature<D>)?.inject(dependencies) }
        } else {
            null
        }
    }
}

inline fun <reified T> loadService() =
    ServiceLoader.load(T::class.java, T::class.java.classLoader).iterator()

/**
 * Singleton implementation of the FeatureManager
 *
 * @property featureMap Dagger multibinding map between the feature and feature provider
 */
@ApplicationScope
class FeatureManagerImpl @Inject constructor(
    override val featureMap: FeatureProviderMap
) : FeatureManager
