package com.ripplearc.heavy.common.features

import com.ripplearc.heavy.common_core.model.DynamicFeature

interface IotHistogramFeature : DynamicFeature<IotHistogramFeature.Dependencies> {
    interface Dependencies : CommonDependencies
}