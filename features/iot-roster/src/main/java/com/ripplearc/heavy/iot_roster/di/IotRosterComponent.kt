package com.ripplearc.heavy.iot_roster.di

import com.ripplearc.heavy.common_core.model.ViewModelFactory
import com.ripplearc.heavy.common.features.IotRosterFeature
import com.ripplearc.heavy.iot_roster.ui.RosterSpinnerFragment
import com.ripplearc.heavy.iot_roster.ui.RosterSpinnerViewModel
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

    fun inject(fragment: RosterSpinnerFragment)

    fun getIotRosterFeature(): IotRosterFeature

    fun getMapViewModel(): ViewModelFactory<RosterSpinnerViewModel>
}
