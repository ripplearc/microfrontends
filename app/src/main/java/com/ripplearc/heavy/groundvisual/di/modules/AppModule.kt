package com.ripplearc.heavy.groundvisual.di.modules

import android.content.Context
import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.common.toolbox.RxCommonPreference
import com.ripplearc.heavy.groundvisual.BuildConfig
import com.ripplearc.heavy.groundvisual.GroundVisualApplication
import dagger.Module
import dagger.Provides
import net.grandcentrix.tray.TrayPreferences


@Module
object AppModule {
    @[Provides ApplicationScope JvmStatic]
    fun provideContext(application: GroundVisualApplication): Context = application

    @[Provides ApplicationScope JvmStatic]
    fun provideTrayPreference(context: Context): TrayPreferences =
        TrayPreferences(context, "common", 1)

    @[Provides ApplicationScope JvmStatic]
    fun provideRxCommonPreference(tray: TrayPreferences): RxCommonPreference =
        RxCommonPreference(tray)
}


