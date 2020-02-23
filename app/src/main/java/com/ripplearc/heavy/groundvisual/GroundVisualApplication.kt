package com.ripplearc.heavy.groundvisual

import android.app.Application
import android.util.Log
import com.orhanobut.logger.Logger
import com.ripplearc.heavy.groundvisual.di.ApplicationComponent
import com.ripplearc.heavy.groundvisual.di.DaggerApplicationComponent

lateinit var appComponent: ApplicationComponent

class GroundVisualApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("GroundVisual", "🤞😎😅")
        inject()
        initLogger()
    }

    private fun inject() {
        appComponent = DaggerApplicationComponent.factory().create(this)
    }

    private fun initLogger() {
        Logger.addLogAdapter(appComponent.getAndroidLogStrategy())
    }


}
