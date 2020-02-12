package com.ripplearc.heavy.common_core.model

interface DynamicFeature<D> : Feature<D> {
	fun inject(dependencies: D)
}
