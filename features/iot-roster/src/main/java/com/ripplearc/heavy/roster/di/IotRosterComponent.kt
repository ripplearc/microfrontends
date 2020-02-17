package com.ripplearc.heavy.iot.roster.di

import com.ripplearc.heavy.common.core.model.ViewModelFactory
import com.ripplearc.heavy.common.features.IotRosterFeature
import com.ripplearc.heavy.iot.roster.ui.RosterSpinnerFragment
import com.ripplearc.heavy.iot.roster.ui.RosterSpinnerViewModel
import com.ripplearc.heavy.roster.di.IotRosterModule
import dagger.BindsInstance
import dagger.Component

@IotRosterScope
@Component(
    dependencies = [IotRosterFeature.Dependencies::class],
    modules = [IotRosterModule::class]
)
interface IotRosterComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: IotRosterFeature.Dependencies,
            @BindsInstance iotRosterFeature: IotRosterFeature
        ): IotRosterComponent
    }

    fun inject(fragment: RosterSpinnerFragment)

    fun getIotRosterFeature(): IotRosterFeature

    fun getMapViewModel(): ViewModelFactory<RosterSpinnerViewModel>
}
