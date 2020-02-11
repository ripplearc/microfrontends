package com.ripplearc.heavy.common_core.qualifier

import com.ripplearc.heavy.common_core.model.Feature
import dagger.MapKey
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FeatureProviderKey(val value: KClass<out Feature<*>>)
