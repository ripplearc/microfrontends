package com.ripplearc.heavy.common.toolbox

import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

fun <T : Any> Single<T>.safeSubscribeBy(
    onError: (Throwable) -> Unit = { e -> e.printStackTrace() },
    onSuccess: (T) -> Unit
) = subscribeBy(
    onSuccess = onSuccess,
    onError = onError
)
