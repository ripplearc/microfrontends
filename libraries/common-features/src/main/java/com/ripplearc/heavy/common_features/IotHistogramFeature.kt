package com.ripplearc.heavy.common_features

import com.ripplearc.heavy.common_core.model.Feature

interface IotHistogramFeature : Feature<IotHistogramFeature.Dependencies> {
    interface Dependencies : CommonDependencies
}