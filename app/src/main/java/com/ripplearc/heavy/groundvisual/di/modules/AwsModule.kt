package com.ripplearc.heavy.groundvisual.di.modules

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.iot.AWSIotClient
import com.ripplearc.heavy.common.core.qualifier.ApplicationScope
import dagger.Module
import dagger.Provides


@Module
object AwsModule {

	@[Provides ApplicationScope]
	fun provideAwsMobileClient(): AWSMobileClient =
		AWSMobileClient.getInstance()

	@[Provides ApplicationScope]
	fun provideAwsConfig(applicationContext: Context): AWSConfiguration =
		AWSConfiguration(applicationContext)

	@[Provides ApplicationScope]
	fun provideAwsIotClient(mobileClient: AWSMobileClient): AWSIotClient =
		AWSIotClient(mobileClient)
			.apply {
				setRegion(Region.getRegion(Regions.US_EAST_1))
			}

}
