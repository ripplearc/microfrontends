package com.ripplearc.heavy.features.di

import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.services.iot.AWSIotClient


/**
 * Aws dependencies needed by feature modules which communicate with Aws.
 */
interface AwsDependencies : Dependencies {
	val awsMobileClient: AWSMobileClient
	val awsConfiguration: AWSConfiguration
	val awsIotClient: AWSIotClient
}
