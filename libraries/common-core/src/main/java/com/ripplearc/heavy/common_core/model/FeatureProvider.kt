package com.ripplearc.heavy.common_core.model

interface FeatureProvider<D: Dependencies> {
    fun get(dependencies: D): Feature<D>
}
