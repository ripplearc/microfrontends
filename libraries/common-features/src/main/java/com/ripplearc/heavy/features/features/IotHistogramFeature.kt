package com.ripplearc.heavy.features.features

import com.ripplearc.heavy.common.features.R
import com.ripplearc.heavy.features.di.CommonDependencies
import kotlin.reflect.KClass

/**
 * Definition of IotHistogramFeature.
 */
interface IotHistogramFeature : DynamicFeature<IotHistogramFeature.Dependencies> {
    interface Dependencies : CommonDependencies
}

val KClass<IotHistogramFeature>.profile
    get() = Feature.Profile(
        id = "histogram",
        sceneId = R.id.sceneIotHistogram
    )
