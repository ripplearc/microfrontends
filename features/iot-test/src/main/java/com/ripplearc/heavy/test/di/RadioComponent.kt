package com.ripplearc.heavy.test.di

import com.ripplearc.heavy.common.features.CommonDependencies
import com.ripplearc.heavy.radio.di.RadioScope
import com.ripplearc.heavy.radio.messaging.MessagingJob
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named


@RadioScope
@Component(
    modules = [AwsModule::class],
    dependencies = [CommonDependencies::class]
)
interface RadioComponent {
    @Component.Factory
    interface Factory {
        fun create(
            dependencies: CommonDependencies,
            @BindsInstance @Named("KeyStorePath") keyStorePath: String,
            @BindsInstance @Named("KeyStoreName") keyStoreName: String,
            @BindsInstance @Named("KeyStorePassword") keyStorePassword: String,
            @BindsInstance @Named("CertificateId") certificateId: String,
            @BindsInstance @Named("IotPolicyName") policyName: String
        ): RadioComponent
    }

    val messagingJob: MessagingJob

}
