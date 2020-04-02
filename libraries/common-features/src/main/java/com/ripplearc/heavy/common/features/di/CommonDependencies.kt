package com.ripplearc.heavy.common.features.di

import android.content.Context
import com.google.gson.Gson
import com.orhanobut.logger.AndroidLogAdapter
import com.ripplearc.heavy.common.rxUtil.RxCommonPreference
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Common dependencies that needed by every feature module. Class ApplicationComponent
 * is the one responsible for implement them.
 */
interface CommonDependencies : Dependencies {
	fun applicationContext(): Context
	fun retrofit(): Retrofit
	fun rxPreference(): RxCommonPreference
	fun getAndroidLogStrategy(): AndroidLogAdapter

	@Named("Pretty")
	fun prettyGson(): Gson
	@Named("Concise")
	fun conciseGson(): Gson
}
