package com.ripplearc.heavy.common.features.platform

import com.ripplearc.heavy.common.features.R
import com.ripplearc.heavy.common.features.di.CommonDependencies
import kotlin.reflect.KClass

/**
 * Definition of IotRosterFeature.
 */
interface IotRosterFeature : Feature<IotRosterFeature.Dependencies> {
    interface Dependencies : CommonDependencies
}
val KClass<IotRosterFeature>.profile
    get() = Feature.Profile(
        id = "roster",
        sceneId = R.id.sceneIotRoster
    )
