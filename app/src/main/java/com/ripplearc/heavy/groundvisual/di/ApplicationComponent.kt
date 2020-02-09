package com.ripplearc.heavy.groundvisual.di

import com.ripplearc.heavy.common_core.model.FeatureProviderMap
import com.ripplearc.heavy.common_core.qualifier.ApplicationScope
import com.ripplearc.heavy.common_features.IotRosterFeature
import com.ripplearc.heavy.groundvisual.GroundVisualApplication
import com.ripplearc.heavy.groundvisual.MainActivity
import com.ripplearc.heavy.groundvisual.di.modules.AppModule
import com.ripplearc.heavy.groundvisual.di.modules.FeaturesModule
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(
    modules = [AppModule::class,
        FeaturesModule::class]
)
interface ApplicationComponent : IotRosterFeature.Dependencies {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: GroundVisualApplication): ApplicationComponent
    }
}
