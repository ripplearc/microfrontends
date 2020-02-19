package com.ripplearc.heavy.iot.test.di

import com.ripplearc.heavy.common.features.IotTestFeature
import dagger.BindsInstance
import dagger.Component

@IotTestScope
@Component(
    dependencies = [IotTestFeature.Dependencies::class]
)
interface IotTestComponent {
    @Component.Factory
    interface Factory {
        fun create(
            dependencies: IotTestFeature.Dependencies,
            @BindsInstance iotTestFeature: IotTestFeature
        ): IotTestComponent
    }

    fun getIotTestFeature(): IotTestFeature
}