package com.ripplearc.heavy.groundvisual.di

import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.common.features.IotHistogramFeature
import com.ripplearc.heavy.common.features.IotRosterFeature
import com.ripplearc.heavy.common.features.IotTestFeature
import com.ripplearc.heavy.groundvisual.GroundVisualApplication
import com.ripplearc.heavy.groundvisual.MainActivity
import com.ripplearc.heavy.groundvisual.di.modules.AppModule
import com.ripplearc.heavy.groundvisual.di.modules.FeaturesModule
import dagger.BindsInstance
import dagger.Component

interface FeatureDependencies :
    IotRosterFeature.Dependencies,
    IotTestFeature.Dependencies,
    IotHistogramFeature.Dependencies

@ApplicationScope
@Component(
    modules = [AppModule::class,
        FeaturesModule::class]
)
interface ApplicationComponent : FeatureDependencies {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: GroundVisualApplication): ApplicationComponent
    }
}
