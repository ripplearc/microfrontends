package com.ripplearc.heavy.toolbelt.aws

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Create instance of PinpointManager.
 */
@Reusable
class PinpointManagerFactory @Inject constructor(
    private val context: Context,
    private val awsConfiguration: AWSConfiguration,
    private val mobileClient: AWSMobileClient
) {
    private var instance: PinpointManager? = null

    /**
     * Create PinpointManager instance if none exists yet.
     * If creating a new one, it first waits for the initialization of AWSMobileClient.
     */
    fun getInstanceSingle(): Single<PinpointManager> =
        instance?.let {
            return Single.just(it)
        } ?: mobileClient.initSingle(context, awsConfiguration)
            .map {
                with(PinpointConfiguration(context, mobileClient, awsConfiguration)) {
                    PinpointManager(this)
                }
            }.doOnSuccess {
                instance = it
            }
}
