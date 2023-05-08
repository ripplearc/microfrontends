package com.ripplearc.heavy.radio.certificate

import android.content.Context
import com.amazonaws.mobileconnectors.iot.AWSIotKeystoreHelper
import com.amazonaws.services.iot.AWSIotClient
import com.amazonaws.services.iot.model.CreateKeysAndCertificateRequest
import com.ripplearc.heavy.toolbelt.constants.Emoji
import com.ripplearc.heavy.toolbelt.rx.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import java.io.File
import java.security.KeyStore
import javax.inject.Inject
import javax.inject.Named

/**
 * The Class KeyStoreFactory tries to load the keys (private) and certificate (public) from
 * the KeyStore. If the Keys and Certificate do not exist, then create them in the AWS Iot and
 * download to the local and save.
 */
class KeyStoreFactory @Inject constructor(
    @param:Named("KeyStorePath") private val keystoreRelativePath: String,
    @param:Named("KeyStoreName") private val keystoreName: String,
    @param:Named("KeyStorePassword") private val keystorePassword: String,
    @param:Named("CertificateId") private val certificateId: String,
    private val iotClient: AWSIotClient,
    private val policyRequestFactory: PolicyRequestFactory,
    private val schedulerFactory: SchedulerFactory,
    private val context: Context
) {
    private val maxAttempts = 2
    private val keystorePath: String by lazy {
        File(context.filesDir, keystoreRelativePath).also {
            it.mkdirs()
        }.absolutePath
    }

    /**
     * Load the KeyStore that contains the Keys and Certificate.
     * @return A single that emits the KeyStore if success. If the KeyStore doesn't
     * exist or failing to load the Keys and Certificate, it creates the Keys and Certiificates
     * in the server, then download and save locally.
     */
    fun loadKeyStoreSingle(): Single<KeyStore> =
        Observable.create<KeyStore>(this::loadKeysAndCert)
            .log(Emoji.Lock)
            .retryToCreateKeyStore()
            .firstOrError()
            .subscribeOn(schedulerFactory.makeComputationScheduler())

    private fun loadKeysAndCert(emitter: ObservableEmitter<KeyStore>) {
        try {
            if (AWSIotKeystoreHelper.isKeystorePresent(keystorePath, keystoreName)) {
                if (AWSIotKeystoreHelper.keystoreContainsAlias(
                        certificateId, keystorePath,
                        keystoreName, keystorePassword
                    )
                ) {
                    AWSIotKeystoreHelper.getIotKeystore(
                        certificateId,
                        keystorePath,
                        keystoreName,
                        keystorePassword
                    )?.let {
                        emitter.onSafeNext(it)
                    } ?: "Keystore $keystorePath/$keystoreName not loaded.".let {
                        emitter.onSafeError(Throwable(it))
                    }
                } else {
                    "Key/cert $certificateId not found in keystore.".let {
                        emitter.onSafeError(Throwable(it))
                    }
                }
            } else {
                "Keystore $keystorePath/$keystoreName not found.".let {
                    emitter.onSafeError(Throwable(it))
                }
            }
        } catch (e: Exception) {
            e.localizedMessage?.let {
                emitter.onSafeError(Throwable(it))
            }
        }
    }

    private fun <T : Any> Observable<T>.retryToCreateKeyStore(): Observable<T> =
        retryWhen { errors ->
            errors
                .enumerated()
                .flatMap { (attemptCount, error) ->
                    if (attemptCount > maxAttempts) {
                        Observable.error<Unit>(error)
                    } else {
                        Observable.fromCallable(this@KeyStoreFactory::genAndSaveKeysAndCert)
                            .log(Emoji.LockAndKey)
                    }
                }
        }


    private fun genAndSaveKeysAndCert() =
        CreateKeysAndCertificateRequest().apply {
            setAsActive = true
        }.run {
            iotClient.createKeysAndCertificate(this)
        }.let { result ->
            AWSIotKeystoreHelper.saveCertificateAndPrivateKey(
                certificateId,
                result.certificatePem,
                result.keyPair.privateKey,
                keystorePath, keystoreName, keystorePassword
            )

            val request = policyRequestFactory.createPolicyRequest(result.certificateArn)
            iotClient.attachPolicy(request)
        }
}