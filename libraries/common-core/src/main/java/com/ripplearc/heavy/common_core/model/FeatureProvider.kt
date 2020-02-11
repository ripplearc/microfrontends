package com.ripplearc.heavy.common_core.model

import javax.inject.Provider

interface FeatureProvider<D> {
    fun get(dependencies: D): Feature<D>
}

typealias FeatureProviderMap =
        @JvmSuppressWildcards
        Map<Class<out Feature<*>>, Provider<FeatureProvider<*>>>
