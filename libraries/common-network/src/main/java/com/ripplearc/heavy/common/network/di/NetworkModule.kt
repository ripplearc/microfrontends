package com.ripplearc.heavy.common.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import com.ripplearc.heavy.common.network.config.NetworkConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
object NetworkModule {
    @Named("Concise")
    @[Provides ApplicationScope]
    fun provideConciseGson() = Gson()

    @Named("Pretty")
    @[Provides ApplicationScope]
    fun providePrettyGson() = GsonBuilder()
        .enableComplexMapKeySerialization()
		.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .setPrettyPrinting().create()

    @[Provides ApplicationScope]
    fun provideOkHttpClient() = OkHttpClient
        .Builder()
        .addInterceptor { chain ->
            chain.request()?.let { request ->
                request.url().newBuilder().build()?.let { url ->
                    request.newBuilder().url(url).build()
                }?.run {
                    chain.proceed(this)
                }
            }
        }
        .build() ?: throw Exception("Cannot instantiate OkHttpClient.")

    @[Provides ApplicationScope]
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(NetworkConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build() ?: throw Exception("Cannot instantiate Retrofit.")

}
