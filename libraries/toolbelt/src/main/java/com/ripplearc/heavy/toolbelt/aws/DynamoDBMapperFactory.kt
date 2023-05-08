package com.ripplearc.heavy.toolbelt.aws

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Create instance of DynamoDBMapper.
 */
@Reusable
class DynamoDBMapperFactory @Inject constructor(
    private val context: Context,
    private val awsConfiguration: AWSConfiguration,
    private val mobileClient: AWSMobileClient
) {
    private var instance: DynamoDBMapper? = null

    /**
     * Create DynamoDBMapper instance if none exists yet.
     * If creating a new one, it first waits for the initialization of AWSMobileClient.
     */
    fun getInstanceSingle(): Single<DynamoDBMapper> =
        instance?.let {
            return Single.just(it)
        } ?: mobileClient.initSingle(context, awsConfiguration)
            .map {
                with(AmazonDynamoDBClient(mobileClient)) {
                    DynamoDBMapper.builder()
                        .dynamoDBClient(this)
                        .awsConfiguration(awsConfiguration)
                        .build()
                }
            }.doOnSuccess {
                instance = it
            }
}
