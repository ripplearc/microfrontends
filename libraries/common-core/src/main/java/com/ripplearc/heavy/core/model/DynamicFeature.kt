package com.ripplearc.heavy.common.core.model

interface DynamicFeature<D> : Feature<D> {
	fun inject(dependencies: D)
}
