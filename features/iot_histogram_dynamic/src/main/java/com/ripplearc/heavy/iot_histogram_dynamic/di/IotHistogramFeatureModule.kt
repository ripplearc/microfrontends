package com.ripplearc.heavy.iot_histogram_dynamic.di

import com.ripplearc.heavy.common_core.model.FeatureProvider
import com.ripplearc.heavy.common_core.qualifier.ApplicationScope
import com.ripplearc.heavy.common_core.qualifier.FeatureProviderKey
import com.ripplearc.heavy.common_features.IotHistogramFeature
import com.ripplearc.heavy.iot_histogram_dynamic.feature.IotHistogramFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
object IotHistogramFeatureModule {
    @[Provides JvmStatic ApplicationScope IntoMap FeatureProviderKey(IotHistogramFeature::class)]
    fun provideIotHistogramFeatureProvider(): FeatureProvider<*> = IotHistogramFeatureImpl
}