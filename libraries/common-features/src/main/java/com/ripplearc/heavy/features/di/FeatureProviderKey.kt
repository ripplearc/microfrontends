package com.ripplearc.heavy.features.di

import com.ripplearc.heavy.features.features.Feature
import dagger.MapKey
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FeatureProviderKey(val value: KClass<out Feature<*>>)
