package com.ripplearc.heavy.groundvisual.di.modules

import com.ripplearc.heavy.common.core.model.FeatureProviderMap
import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.common.features.FeatureManager
import com.ripplearc.heavy.common.features.FeatureManagerImpl
import com.ripplearc.heavy.iot.roster.di.IotRosterFeatureModule
import com.ripplearc.heavy.iot.test.di.IotTestFeatureModule
import dagger.Module
import dagger.Provides

@Module(
    includes = [IotRosterFeatureModule::class,
        IotTestFeatureModule::class]
)
object FeaturesModule {
    @[Provides ApplicationScope]
    fun provideFeatureManager(map: FeatureProviderMap): FeatureManager {
        return FeatureManagerImpl(map)
    }
}


