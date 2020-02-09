package com.ripplearc.heavy.groundvisual.di.modules

import android.content.Context
import com.ripplearc.heavy.common_core.qualifier.ApplicationScope
import com.ripplearc.heavy.groundvisual.GroundVisualApplication
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet


@Module
object AppModule {
    @Provides
    @ApplicationScope
    @JvmStatic
    fun provideContext(application: GroundVisualApplication): Context = application

    @Provides
    @JvmStatic
    @IntoSet
    fun provideOneString(): String {
        return "ABC"
    }
}


