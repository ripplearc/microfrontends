package com.ripplearc.heavy.common.core.qualifier

import com.ripplearc.heavy.common.core.model.Feature
import dagger.MapKey
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FeatureProviderKey(val value: KClass<out Feature<*>>)
