package com.ripplearc.heavy.common.features

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.services.iot.AWSIotClient
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
    fun prettyGson(): Gson

    @Named("Concise")
    fun conciseGson(): Gson
    fun rxPreference(): RxCommonPreference
    fun coroutinesThreadPoolContext(): ExecutorCoroutineDispatcher
}

interface AwsDependencies : Dependencies {
    val awsMobileClient: AWSMobileClient
    val awsConfiguration: AWSConfiguration
    val awsIotClient: AWSIotClient
}

interface RadioDependencies : Dependencies {

}

