package com.ripplearc.heavy.iot_roster.di

import com.ripplearc.heavy.common_core.model.FeatureProvider
import com.ripplearc.heavy.common_core.qualifier.ApplicationScope
import com.ripplearc.heavy.common_core.qualifier.FeatureProviderKey
import com.ripplearc.heavy.common.features.IotRosterFeature
import com.ripplearc.heavy.iot_roster.feature.IotRosterFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module
object IotRosterFeatureModule {

    @[Provides JvmStatic ApplicationScope IntoMap FeatureProviderKey(IotRosterFeature::class)]
    fun provideIotRosterFeatureProvider(): FeatureProvider<*> {
        return IotRosterFeatureImpl
    }
}
