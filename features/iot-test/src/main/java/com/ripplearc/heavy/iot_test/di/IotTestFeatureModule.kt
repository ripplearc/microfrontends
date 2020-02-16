package com.ripplearc.heavy.iot_test.di

import com.ripplearc.heavy.common_core.model.FeatureProvider
import com.ripplearc.heavy.common_core.qualifier.ApplicationScope
import com.ripplearc.heavy.common_core.qualifier.FeatureProviderKey
import com.ripplearc.heavy.common.features.IotTestFeature
import com.ripplearc.heavy.iot_test.feature.IotTestFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
object IotTestFeatureModule {
    @[Provides JvmStatic ApplicationScope IntoMap FeatureProviderKey(IotTestFeature::class)]
    fun provideIotTestFeatureProvider(): FeatureProvider<*> = IotTestFeatureImpl
}