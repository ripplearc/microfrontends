package com.ripplearc.heavy.common_features

import com.ripplearc.heavy.common_core.model.Feature

interface IotTestFeature : Feature<IotTestFeature.Dependencies> {
	interface Dependencies : CommonDependencies
}
