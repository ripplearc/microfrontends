package com.ripplearc.heavy.common.features.manager

import com.ripplearc.heavy.common.features.platform.Feature
import javax.inject.Provider

/**
 * Provider of feature implementation
 *
 * @param D dependencies of the feature
 */
interface FeatureProvider<D> {
    fun get(dependencies: D): Feature<D>
}

/**
 * Dagger multibindings map between feature class and feature implementation.
 */
typealias FeatureProviderMap =
        @JvmSuppressWildcards
        Map<Class<out Feature<*>>, Provider<FeatureProvider<*>>>
