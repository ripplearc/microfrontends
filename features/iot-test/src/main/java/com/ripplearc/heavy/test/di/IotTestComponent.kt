package com.ripplearc.heavy.iot.test.di

import com.ripplearc.heavy.common.features.IotTestFeature
import com.ripplearc.heavy.common.features.RadioDependencies
import com.ripplearc.heavy.radio.di.AwsModule
import com.ripplearc.heavy.test.di.RadioComponent
import com.ripplearc.heavy.test.ui.RequestActivity
import dagger.BindsInstance
import dagger.Component

@IotTestScope
@Component(
    modules = [AwsModule::class],
    dependencies = [IotTestFeature.Dependencies::class, RadioComponent::class]
)
interface IotTestComponent {
    @Component.Factory
    interface Factory {
        fun create(
            dependencies: IotTestFeature.Dependencies,
            radioDependencies: RadioComponent,
            @BindsInstance iotTestFeature: IotTestFeature
        ): IotTestComponent
    }

    fun getIotTestFeature(): IotTestFeature
    fun inject(activity: RequestActivity)
}