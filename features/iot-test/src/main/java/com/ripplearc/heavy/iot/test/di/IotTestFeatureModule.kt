package com.ripplearc.heavy.iot.test.di

import com.ripplearc.heavy.common.features.manager.FeatureProvider
import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.common.features.di.FeatureProviderKey
import com.ripplearc.heavy.common.features.platform.IotTestFeature
import com.ripplearc.heavy.iot.test.feature.IotTestFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
object IotTestFeatureModule {
    @[Provides ApplicationScope IntoMap FeatureProviderKey(IotTestFeature::class)]
    fun provideIotTestFeatureProvider(): FeatureProvider<*> = IotTestFeatureImpl
}
