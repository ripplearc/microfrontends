package com.ripplearc.heavy.common.features.platform

import com.ripplearc.heavy.common.features.R
import com.ripplearc.heavy.common.features.di.AwsDependencies
import com.ripplearc.heavy.common.features.di.CommonDependencies
import kotlin.reflect.KClass

/**
 * Definition of IotTestFeature.
 */
interface IotTestFeature : Feature<IotTestFeature.Dependencies> {
    interface Dependencies : CommonDependencies, AwsDependencies
}

val KClass<IotTestFeature>.profile
    get() = Feature.Profile(
        id = "test",
        sceneId = R.id.sceneIotTest
    )

