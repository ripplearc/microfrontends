package com.ripplearc.heavy.common_features

import com.ripplearc.heavy.common_core.model.Dependencies
import com.ripplearc.heavy.common_core.model.Feature
import com.ripplearc.heavy.common_core.model.FeatureProvider
import com.ripplearc.heavy.common_core.model.FeatureProviderMap
import com.ripplearc.heavy.common_core.qualifier.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

interface FeatureManager {
    //    val features: List<KClass<out Feature<out CommonDependencies>>>
//    fun <D : Dependencies> registerStaticFeature(
//        feature: KClass<Feature<D>>,
//        impl: KClass<out Feature<D>>
//    )
    val featureMap: FeatureProviderMap
}


inline fun <reified T : Feature<D>, D> FeatureManager.getFeature(dependencies: D): T? {
//    features.firstOrNull {
//
//    }
//    return featureMap
    return null
}


@ApplicationScope
class FeatureManagerImpl @Inject constructor(
    override val featureMap: FeatureProviderMap
) : FeatureManager {

}


//    var concurrentMap = HashMap<KClass<Feature<D>>, KClass<Feature<D>>>()

//    fun <D> registerStaticFeature(
//        feature: KClass<Feature<D>>,
//        impl: KClass<out Feature<D>>
//    ) {
//
//    }


//    override val features = mapOf(
////    override val features: Map<KClass<out Feature<out CommonDependencies>>> = mapOf(
////    override val features = listOf(
////        IotTestFeature::class to IotTestFeatureImpl::class,
//        IotRosterFeature::class to
//    )
//}
