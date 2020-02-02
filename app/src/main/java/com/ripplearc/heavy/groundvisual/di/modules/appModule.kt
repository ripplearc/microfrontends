package com.ripplearc.heavy.groundvisual.di.modules

import android.content.Context
import com.ripplearc.heavy.groundvisual.GroundVisualApplication
import com.ripplearc.heavy.groundvisual.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
object AppModule {
	@Provides
	@ApplicationScope
	@JvmStatic
	fun provideContext(application: GroundVisualApplication): Context = application
}
