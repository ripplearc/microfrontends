package com.ripplearc.heavy.common.features.platform

/**
 * Feature on demand to be loaded from the Google Play
 *
 * @param D dependencies of the feature
 */
interface DynamicFeature<D> : Feature<D> {
	fun inject(dependencies: D)
}
