package com.ripplearc.heavy.common.features.di

import com.ripplearc.heavy.common.features.platform.Feature
import dagger.MapKey
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FeatureProviderKey(val value: KClass<out Feature<*>>)
