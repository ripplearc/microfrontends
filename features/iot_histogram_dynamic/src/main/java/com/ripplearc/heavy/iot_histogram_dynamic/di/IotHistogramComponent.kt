package com.ripplearc.heavy.iot_histogram_dynamic.di

import com.ripplearc.heavy.common_features.IotHistogramFeature
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