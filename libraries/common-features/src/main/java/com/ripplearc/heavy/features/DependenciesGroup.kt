package com.ripplearc.heavy.common.features

import android.content.Context
import com.google.gson.Gson
import com.ripplearc.heavy.common.core.model.Dependencies
import com.ripplearc.heavy.common.toolbox.RxCommonPreference
import retrofit2.Retrofit


interface CommonDependencies : Dependencies {
    fun applicationContext(): Context
    fun retrofit(): Retrofit
    fun gson(): Gson
    fun rxPreference(): RxCommonPreference
}

interface RadioDependencies : Dependencies {

}

