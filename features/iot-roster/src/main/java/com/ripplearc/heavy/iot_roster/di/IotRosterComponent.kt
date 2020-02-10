package com.ripplearc.heavy.iot_roster.di

import com.ripplearc.heavy.common_features.IotRosterFeature
import dagger.BindsInstance
import dagger.Component

@IotRosterScope
@Component(
    dependencies = [IotRosterFeature.Dependencies::class]
)
interface IotRosterComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: IotRosterFeature.Dependencies,
            @BindsInstance iotRosterFeature: IotRosterFeature
        ): IotRosterComponent
    }

    fun getIotRosterFeature(): IotRosterFeature
}
