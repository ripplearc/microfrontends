package com.ripplearc.heavy.iot.dynamic.histogram.di

import com.ripplearc.heavy.common.core.model.ViewModelFactory
import com.ripplearc.heavy.features.features.IotHistogramFeature
import com.ripplearc.heavy.histogram.ui.HistogramActivity
import com.ripplearc.heavy.iot.dynamic.histogram.ui.IotHistogramFragment
import com.ripplearc.heavy.iot.dynamic.histogram.ui.IotHistogramViewModel
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
            dependencies: IotHistogramFeature.Dependencies,
            @BindsInstance IotTestFeature: IotHistogramFeature
        ): IotHistogramComponent
    }

    fun getIotHistogramFeature(): IotHistogramFeature
    fun inject(activity: HistogramActivity)
    fun inject(fragment: IotHistogramFragment)
    fun getHistogramViewModel(): ViewModelFactory<IotHistogramViewModel>
}