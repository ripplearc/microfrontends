package com.ripplearc.heavy.common.features

import android.content.Context
import com.google.gson.Gson
import com.ripplearc.heavy.common.core.model.Dependencies
import com.ripplearc.heavy.common.rxUtil.RxCommonPreference
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Named


interface CommonDependencies : Dependencies {
    fun applicationContext(): Context
    fun retrofit(): Retrofit

    @Named("Pretty")
    fun pretyyGson(): Gson

    @Named("Concise")
    fun conciseGson(): Gson
    fun rxPreference(): RxCommonPreference
    fun coroutinesThreadPoolContext(): ExecutorCoroutineDispatcher
}

interface RadioDependencies : Dependencies {

}

