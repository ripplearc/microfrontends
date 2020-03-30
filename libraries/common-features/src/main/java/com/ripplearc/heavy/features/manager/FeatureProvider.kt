package com.ripplearc.heavy.features.manager

import com.ripplearc.heavy.features.features.Feature
import javax.inject.Provider

interface FeatureProvider<D> {
    fun get(dependencies: D): Feature<D>
}

typealias FeatureProviderMap =
        @JvmSuppressWildcards
        Map<Class<out Feature<*>>, Provider<FeatureProvider<*>>>
