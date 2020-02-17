package com.ripplearc.heavy.iot.roster.di

import com.ripplearc.heavy.common.core.model.FeatureProvider
import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.common.core.qualifier.FeatureProviderKey
import com.ripplearc.heavy.common.features.IotRosterFeature
import com.ripplearc.heavy.iot.roster.feature.IotRosterFeatureImpl
import com.ripplearc.heavy.roster.service.DeviceRosterService
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit


@Module
object IotRosterFeatureModule {

    @[Provides JvmStatic ApplicationScope IntoMap FeatureProviderKey(IotRosterFeature::class)]
    fun provideIotRosterFeatureProvider(): FeatureProvider<*> {
        return IotRosterFeatureImpl
    }

}
