package com.ripplearc.heavy.features

import com.ripplearc.heavy.common.core.model.Feature
import com.ripplearc.heavy.common.features.IotHistogramFeature
import com.ripplearc.heavy.common.features.IotRosterFeature
import com.ripplearc.heavy.common.features.IotTestFeature
import com.ripplearc.heavy.common.features.R
import kotlin.reflect.KClass

/**
 * Assign id and sceneId for each feature profile.
 * id is typically used as start feature from action intent.
 * sceneId is used for in app navigation.
 */
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