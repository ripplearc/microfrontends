package com.ripplearc.heavy.iot_roster.di.di

import com.ripplearc.heavy.common_core.model.FeatureProvider
import com.ripplearc.heavy.common_core.qualifier.FeatureProviderKey
import com.ripplearc.heavy.common_features.IotRosterFeature
import com.ripplearc.heavy.iot_roster.di.feature.IotRosterFeatureImpl
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet

//@IotRosterScope
@Component(
    modules = [IotRosterModule::class],
    dependencies = [IotRosterFeature.Dependencies::class]
)
interface IotRosterComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: IotRosterFeature.Dependencies,
            @BindsInstance iotRosterFeature: IotRosterFeature
        ): IotRosterComponent
    }

    fun getIotRosterFeature(): IotRosterFeature
}

@Module
object IotRosterModule {
    @[Provides JvmStatic IntoSet]
    fun provideOneString(): String {
        return "XYZ"
    }

    @[Provides IntoMap FeatureProviderKey(IotRosterFeature::class)]
    fun provideIotRosterFeatureProvider(): FeatureProvider<*> {
        return IotRosterFeatureImpl
    }
}
