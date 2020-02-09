package com.ripplearc.heavy.common_features

import com.ripplearc.heavy.common_core.model.Dependencies
import com.ripplearc.heavy.common_core.model.Feature
import kotlin.reflect.KClass

interface FeatureManager {
    //    val features: List<KClass<out Feature<out CommonDependencies>>>
    fun <D : Dependencies> registerStaticFeature(
        feature: KClass<Feature<D>>,
        impl: KClass<out Feature<D>>
    )
}


inline fun <reified T : Feature<D>, D> FeatureManager.getFeature(dependencies: D): T? {
//    features.firstOrNull {
//
//    }
    return null
}


//@ApplicationScope
//class FeatureManagerImpl @Inject constructor() : FeatureManager {
//
//    private var featureMap = emptyMap<KClass<Feature<Dependencies>>, KClass<Feature<Dependencies>>>()
//
//    override fun <D : Dependencies> registerStaticFeature(
//        feature: KClass<Feature<D>>,
//        impl: KClass<out Feature<D>>
//    ) {
//        featureMap[feature] = impl
//    }


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
