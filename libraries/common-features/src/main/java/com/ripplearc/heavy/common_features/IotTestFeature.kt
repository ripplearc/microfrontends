package com.ripplearc.heavy.common_features

import android.content.Context

interface IotTestFeature: Feature {
	fun fetchStatus()

	interface Dependenceis {
		val applicationContext: Context
	}
}
