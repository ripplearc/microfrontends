package com.ripplearc.heavy.iot.test.di

import com.ripplearc.heavy.common.features.IotTestFeature
import com.ripplearc.heavy.radio.di.AwsModule
import com.ripplearc.heavy.radio.messaging.MessagingJob
import com.ripplearc.heavy.test.ui.RequestActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@IotTestScope
@Component(
    modules = [AwsModule::class],
    dependencies = [IotTestFeature.Dependencies::class]
)
interface IotTestComponent {
    @Component.Factory
    interface Factory {
        fun create(
            dependencies: IotTestFeature.Dependencies,
            @BindsInstance iotTestFeature: IotTestFeature,
            @BindsInstance @Named("KeyStorePath") keyStorePath: String,
            @BindsInstance @Named("KeyStoreName") keyStoreName: String,
            @BindsInstance @Named("KeyStorePassword") keyStorePassword: String,
            @BindsInstance @Named("CertificateId") certificateId: String,
            @BindsInstance @Named("IotPolicyName") policyName: String
        ): IotTestComponent
    }

    fun getIotTestFeature(): IotTestFeature
    fun inject(activity: RequestActivity)
    val messagingJob: MessagingJob
}