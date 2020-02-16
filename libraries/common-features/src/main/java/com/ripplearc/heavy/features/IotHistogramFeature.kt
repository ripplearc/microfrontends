package com.ripplearc.heavy.common.features

import com.ripplearc.heavy.common.core.model.DynamicFeature

interface IotHistogramFeature : DynamicFeature<IotHistogramFeature.Dependencies> {
    interface Dependencies : CommonDependencies
}