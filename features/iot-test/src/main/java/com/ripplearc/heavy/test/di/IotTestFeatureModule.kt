package com.ripplearc.heavy.iot.test.di

import com.ripplearc.heavy.features.manager.FeatureProvider
import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.features.di.FeatureProviderKey
import com.ripplearc.heavy.features.features.IotTestFeature
import com.ripplearc.heavy.iot.test.feature.IotTestFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
object IotTestFeatureModule {
    @[Provides JvmStatic ApplicationScope IntoMap FeatureProviderKey(IotTestFeature::class)]
    fun provideIotTestFeatureProvider(): FeatureProvider<*> = IotTestFeatureImpl
}