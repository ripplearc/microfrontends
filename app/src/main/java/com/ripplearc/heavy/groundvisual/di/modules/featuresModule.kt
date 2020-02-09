package com.ripplearc.heavy.groundvisual.di.modules

import com.ripplearc.heavy.common_core.model.FeatureProviderMap
import com.ripplearc.heavy.common_features.FeatureManager
import com.ripplearc.heavy.common_features.FeatureManagerImpl
import com.ripplearc.heavy.iot_roster.di.di.IotRosterFeatureModule
import dagger.Module
import dagger.Provides

@Module (
    includes = [IotRosterFeatureModule::class]
)
object FeaturesModule {
    @[Provides JvmStatic]
    fun provideFeatureManager(map: FeatureProviderMap): FeatureManager {
        return FeatureManagerImpl(map)
    }
}


