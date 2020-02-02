package com.ripplearc.heavy.groundvisual.di

import androidx.annotation.BinderThread
import com.ripplearc.heavy.common_features.IotTestFeature
import com.ripplearc.heavy.groundvisual.GroundVisualApplication
import com.ripplearc.heavy.groundvisual.di.modules.AppModule
import com.ripplearc.heavy.groundvisual.di.modules.FeaturesModule
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(modules = [AppModule::class, FeaturesModule::class])
interface ApplicationComponent : IotTestFeature.Dependenceis {

	fun iotTestFeature(): IotTestFeature?

	@Component.Factory
	interface Factory {
		fun create(@BindsInstance application: GroundVisualApplication): ApplicationComponent
	}
}
