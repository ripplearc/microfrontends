package com.ripplearc.heavy.common.features

import com.ripplearc.heavy.common.core.model.DynamicFeature
import com.ripplearc.heavy.common.core.model.Feature
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
