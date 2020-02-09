package com.ripplearc.heavy.groundvisual.di.modules

import com.ripplearc.heavy.common_features.IotTestFeature
import com.ripplearc.heavy.groundvisual.di.ApplicationComponent
import dagger.Module
import dagger.Provides

@Module
object FeaturesModule {
	@[Provides JvmStatic]
	fun provideFeatureManager(): IotTestFeature? {
		return null
	}
}
