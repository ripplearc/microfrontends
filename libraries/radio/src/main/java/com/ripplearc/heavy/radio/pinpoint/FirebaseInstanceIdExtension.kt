package com.ripplearc.heavy.radio.pinpoint

import com.google.firebase.iid.FirebaseInstanceId
import com.orhanobut.logger.Logger
import com.ripplearc.heavy.toolbelt.constants.Emoji
import io.reactivex.Single


/**
 * Wrapper of the FirebaseInstanceId completion handler to an observable.
 */
fun FirebaseInstanceId.getTokenSingle(): Single<String> =
    Single.create<String> { emitter ->
        instanceId
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Logger.t(Emoji.Ticket).w("FCM Token getInstanceId failed ${task.exception}")
                    emitter.onError(Throwable(task.exception))
                } else {
                    task.result?.token?.let {
                        emitter.onSuccess(it)
                        Logger.t(Emoji.Ticket).d("FCM token: $it")
                    }
                }
            }
    }