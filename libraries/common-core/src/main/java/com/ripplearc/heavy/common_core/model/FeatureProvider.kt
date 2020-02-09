package com.ripplearc.heavy.common_core.model

import javax.inject.Provider

interface FeatureProvider<D : Dependencies> {
    fun get(dependencies: D): Feature<D>
}

typealias FeatureProviderMap =
        Map<Class<out Feature<*>>, Provider<FeatureProvider<*>>>
