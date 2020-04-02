package com.ripplearc.heavy.iot.test.di

import com.ripplearc.heavy.common.features.platform.IotTestFeature
import com.ripplearc.heavy.iot.test.ui.RequestFragment
import com.ripplearc.heavy.radio.di.RadioComponent
import com.ripplearc.heavy.test.ui.RequestActivity
import dagger.BindsInstance
import dagger.Component

@IotTestScope
@Component(
    dependencies = [IotTestFeature.Dependencies::class, RadioComponent::class]
)
internal interface IotTestComponent {
    @Component.Factory
    interface Factory {
        fun create(
			dependencies: IotTestFeature.Dependencies,
			radioDependencies: RadioComponent,
			@BindsInstance iotTestFeature: IotTestFeature
        ): IotTestComponent
    }

    fun getIotTestFeature(): IotTestFeature
    fun inject(fragment: RequestFragment)
    fun inject(activity: RequestActivity)

}
