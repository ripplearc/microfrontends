package com.ripplearc.heavy.test.di

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.regions.Regions
import com.amazonaws.services.iot.AWSIotClient
import com.ripplearc.heavy.radio.di.RadioScope
import dagger.Module
import dagger.Provides


@Module
object AwsModule {

    @JvmStatic
    @Provides
    @RadioScope
    fun provideAwsMobileClient(): AWSMobileClient =
        AWSMobileClient.getInstance()

    @JvmStatic
    @Provides
    @RadioScope
    fun provideAwsConfig(applicationContext: Context): AWSConfiguration =
        AWSConfiguration(applicationContext)

    @JvmStatic
    @Provides
    @RadioScope
    fun provideAwsIotClient(mobileClient: AWSMobileClient): AWSIotClient =
        AWSIotClient(mobileClient)
            .apply {
                Regions.US_EAST_1
            }

}
