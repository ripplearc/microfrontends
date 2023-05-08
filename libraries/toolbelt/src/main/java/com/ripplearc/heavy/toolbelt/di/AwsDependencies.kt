package com.ripplearc.heavy.toolbelt.di

import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.services.iot.AWSIotClient

interface AwsDependencies {
    val awsMobileClient: AWSMobileClient
    val awsConfiguration: AWSConfiguration
    val awsIotClient: AWSIotClient
}