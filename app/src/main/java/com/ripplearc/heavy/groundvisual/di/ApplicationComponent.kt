package com.ripplearc.heavy.groundvisual.di

import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.common.features.platform.IotHistogramFeature
import com.ripplearc.heavy.common.features.platform.IotRosterFeature
import com.ripplearc.heavy.common.features.platform.IotTestFeature
import com.ripplearc.heavy.common.network.di.NetworkModule
import com.ripplearc.heavy.groundvisual.featureLink.FeatureLinkActivity
import com.ripplearc.heavy.groundvisual.GroundVisualApplication
import com.ripplearc.heavy.groundvisual.MainActivity
import com.ripplearc.heavy.groundvisual.di.modules.AppModule
import com.ripplearc.heavy.groundvisual.di.modules.AwsModule
import com.ripplearc.heavy.groundvisual.di.modules.FeaturesModule
import com.ripplearc.heavy.groundvisual.di.modules.LogModule
import dagger.BindsInstance
import dagger.Component

interface FeatureDependencies :
    IotRosterFeature.Dependencies,
    IotTestFeature.Dependencies,
    IotHistogramFeature.Dependencies

@ApplicationScope
@Component(
    modules = [AppModule::class,
        FeaturesModule::class,
        NetworkModule::class,
        LogModule::class,
        AwsModule::class]
)
interface ApplicationComponent : FeatureDependencies {

    fun inject(activity: MainActivity)
    fun inject(activity: FeatureLinkActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: GroundVisualApplication): ApplicationComponent
    }

}
