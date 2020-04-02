package com.ripplearc.heavy.common.rxUtil

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

fun <T : Any> Single<T>.safeSubscribeBy(
    onError: (Throwable) -> Unit = { e -> e.printStackTrace() },
    onSuccess: (T) -> Unit
) = subscribeBy(
    onSuccess = onSuccess,
    onError = onError
)

fun Completable.safeSubscribeBy(
    onError: (Throwable) -> Unit = { e -> e.printStackTrace() },
    onComplete: () -> Unit = {}
) = subscribeBy(
    onComplete = onComplete,
    onError = onError
)

fun <T : Any> Observable<T>.safeSubscribeBy(
    onError: (Throwable) -> Unit = { e -> e.printStackTrace() },
    onNext: (T) -> Unit,
    onComplete: () -> Unit = {}
) = subscribeBy(
    onNext = onNext,
    onError = onError,
    onComplete = onComplete
)
