package com.ripplearc.heavy.features

import com.ripplearc.heavy.common.core.model.Feature
import com.ripplearc.heavy.common.features.IotHistogramFeature
import com.ripplearc.heavy.common.features.IotRosterFeature
import com.ripplearc.heavy.common.features.IotTestFeature
import com.ripplearc.heavy.common.features.R
import kotlin.reflect.KClass

val <T : Feature<*>> KClass<T>.profile
    get() = when (this) {
        IotRosterFeature::class -> Feature.Profile(
            id = "roster",
            sceneId = R.id.sceneIotRoster
        )
        IotTestFeature::class -> Feature.Profile(
            id = "test",
            sceneId = R.id.sceneIotTest
        )
        IotHistogramFeature::class -> Feature.Profile(
            id = "histogram",
            sceneId = R.id.sceneIotHistogram
        )
        else -> throw Exception("Feature doesn't exist!")
    }