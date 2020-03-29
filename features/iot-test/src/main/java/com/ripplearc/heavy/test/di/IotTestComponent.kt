package com.ripplearc.heavy.iot.test.di

import com.ripplearc.heavy.common.core.model.ViewModelFactory
import com.ripplearc.heavy.common.features.IotTestFeature
import com.ripplearc.heavy.iot.test.ui.RequestFragment
import com.ripplearc.heavy.iot.test.ui.RequestViewModel
import com.ripplearc.heavy.radio.di.RadioComponent
import com.ripplearc.heavy.test.ui.RequestActivity
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExecutorCoroutineDispatcher

@IotTestScope
@Component(
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
    fun inject(fragment: RequestFragment)
    fun inject(activity: RequestActivity)
    fun getMapViewModel(): ViewModelFactory<RequestViewModel>
    fun coroutinesThreadPoolContext(): ExecutorCoroutineDispatcher

}