package com.ripplearc.heavy.radio.di

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.services.iot.AWSIotClient
import com.ripplearc.heavy.radio.messaging.MessagingJob
import com.ripplearc.heavy.toolbelt.di.AwsDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

interface RadioDependencies {
    val messagingJob: MessagingJob
}

@RadioScope
@Component
interface RadioComponent : RadioDependencies {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance awsMobileClient: AWSMobileClient,
            @BindsInstance awsConfiguration: AWSConfiguration,
            @BindsInstance awsIotClient: AWSIotClient,
            @BindsInstance context: Context,
            @BindsInstance @Named("KeyStorePath") keyStorePath: String,
            @BindsInstance @Named("KeyStoreName") keyStoreName: String,
            @BindsInstance @Named("KeyStorePassword") keyStorePassword: String,
            @BindsInstance @Named("CertificateId") certificateId: String,
            @BindsInstance @Named("IotPolicyName") policyName: String
        ): RadioComponent
    }

    override val messagingJob: MessagingJob
}