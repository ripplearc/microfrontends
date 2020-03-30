package com.ripplearc.heavy.features.features

interface DynamicFeature<D> : Feature<D> {
	fun inject(dependencies: D)
}
