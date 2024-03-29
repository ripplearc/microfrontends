package com.ripplearc.heavy.toolbelt.rx

import io.reactivex.ObservableEmitter
import io.reactivex.SingleEmitter

fun <T : Any> ObservableEmitter<T>.onSafeError(error: Throwable) {
    if (!isDisposed) {
        onError(error)
    }
}

fun <T : Any> ObservableEmitter<T>.onSafeNext(value: T) {
    if (!isDisposed) {
        onNext(value)
    }
}

fun <T : Any> ObservableEmitter<T>.onSafeComplete() {
    if (!isDisposed) {
        onComplete()
    }
}

fun <T : Any> SingleEmitter<T>.onSafeSuccess(value: T) {
    if (!isDisposed) {
        onSuccess(value)
    }
}

fun <T : Any> SingleEmitter<T>.onSafeError(error: Throwable) {
    if (!isDisposed) {
        onError(error)
    }
}
