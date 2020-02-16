package com.ripplearc.heavy.common.features

import com.ripplearc.heavy.common.core.model.Feature

interface IotTestFeature : Feature<IotTestFeature.Dependencies> {
	interface Dependencies : CommonDependencies
}
