package com.ripplearc.heavy.iot.dynamic.histogram.di

import com.ripplearc.heavy.common.features.IotHistogramFeature
import dagger.BindsInstance
import dagger.Component

@IotHistogramScope
@Component(
    dependencies = [IotHistogramFeature.Dependencies::class]
)
interface IotHistogramComponent {
    @Component.Factory
    interface Factory {
        fun create(
            deprecated: IotHistogramFeature.Dependencies,
            @BindsInstance IotTestFeature: IotHistogramFeature
        ): IotHistogramComponent
    }

    fun getIotHistogramFeature(): IotHistogramFeature
}