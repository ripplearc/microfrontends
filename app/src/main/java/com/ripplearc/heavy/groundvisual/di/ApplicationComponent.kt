package com.ripplearc.heavy.groundvisual.di

import com.ripplearc.heavy.common_core.model.Feature
import com.ripplearc.heavy.common_core.model.FeatureProvider
import com.ripplearc.heavy.common_core.model.FeatureProviderMap
import com.ripplearc.heavy.common_core.qualifier.ApplicationScope
import com.ripplearc.heavy.common_features.IotRosterFeature
import com.ripplearc.heavy.common_features.IotTestFeature
import com.ripplearc.heavy.groundvisual.GroundVisualApplication
import com.ripplearc.heavy.groundvisual.MainActivity
import com.ripplearc.heavy.groundvisual.di.modules.AppModule
import com.ripplearc.heavy.groundvisual.di.modules.FeaturesModule
import com.ripplearc.heavy.iot_roster.di.di.IotRosterModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Provider
import kotlin.reflect.KClass


@ApplicationScope
@Component(
    modules = [AppModule::class,
        IotRosterModule::class,
        FeaturesModule::class]
)
interface ApplicationComponent : IotRosterFeature.Dependencies {

    fun iotTestFeature(): IotTestFeature?

    fun getFeatureMap(): FeatureProviderMap

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: GroundVisualApplication): ApplicationComponent
    }
}
