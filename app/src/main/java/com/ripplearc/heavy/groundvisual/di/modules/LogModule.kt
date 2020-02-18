package com.ripplearc.heavy.groundvisual.di.modules


import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.PrettyFormatStrategy
import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.groundvisual.R
import dagger.Module
import dagger.Provides

@Module
object LogModule {
    @[Provides ApplicationScope JvmStatic]
    fun provideAndroidLogAdapter(context: Context) =
        PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .tag(context.getString(R.string.app_name))
            .build()
            .let {
                AndroidLogAdapter(it)
            }
}

