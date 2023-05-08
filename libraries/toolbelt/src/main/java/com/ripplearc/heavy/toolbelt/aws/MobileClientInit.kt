package com.ripplearc.heavy.toolbelt.aws

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobile.config.AWSConfiguration
import com.ripplearc.heavy.toolbelt.rx.onSafeError
import com.ripplearc.heavy.toolbelt.rx.onSafeSuccess
import io.reactivex.Single


/**
 * An AWSMobileClient extension that loads the `awsconfiguration.json` and initializes.
 * It emits success after the initialization succeeds.
 */
fun AWSMobileClient.initSingle(
    context: Context,
    awsConfig: AWSConfiguration
): Single<UserStateDetails> =
    Single.create<UserStateDetails> { emitter ->
        initialize(context, awsConfig, object : Callback<UserStateDetails> {
            override fun onResult(result: UserStateDetails?) {
                result?.let {
                    emitter.onSafeSuccess(it)
                }
            }

            override fun onError(e: Exception?) {
                e?.let { emitter.onSafeError(e) }
            }
        })
    }
