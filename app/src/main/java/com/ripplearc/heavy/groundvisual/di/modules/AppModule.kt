package com.ripplearc.heavy.groundvisual.di.modules

import android.content.Context
import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.common.toolbox.RxPreference
import com.ripplearc.heavy.groundvisual.GroundVisualApplication
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet


@Module
object AppModule {
    @[Provides ApplicationScope JvmStatic]
    fun provideContext(application: GroundVisualApplication): Context = application

    @[Provides ApplicationScope JvmStatic]
    fun providePreference(context: Context): RxPreference = RxPreference.create(context)
}


