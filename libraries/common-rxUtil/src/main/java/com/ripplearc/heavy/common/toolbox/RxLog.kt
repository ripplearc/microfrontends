@file:Suppress("NOTHING_TO_INLINE")

package com.ripplearc.heavy.common.toolbox

import com.orhanobut.logger.Logger
import io.reactivex.*

inline fun <reified T> printEvent(tag: String, success: T?, error: Throwable?) {
    when {
        success == null && error == null -> Logger.t(tag).d("Complete") /* Only with Maybe */
        success != null -> Logger.t(tag).d("Success $success")
        error != null -> Logger.t(tag).e("Error $error")
        else -> -1 /* Cannot happen*/
    }
}

inline fun printEvent(tag: String, error: Throwable?) =
    when {
        error != null -> Logger.t(tag).e("Error $error")
        else -> Logger.t(tag).d("Complete")
    }

inline fun tag() =
    Thread.currentThread().stackTrace
        .first { it.fileName.endsWith(".kt") }
        .let { stack -> "⚠️ ⚠️ ⚠️ PhotographerRx: " + "${stack.fileName.removeSuffix(".kt")}::${stack.methodName}:${stack.lineNumber}" }

inline fun <reified T> Single<T>.log(tag: String? = null): Single<T> {
    val line = tag ?: tag()
    return doOnEvent { success, error -> printEvent(line, success, error) }
        .doOnSubscribe { Logger.t(line).d("Subscribe") }
        .doOnDispose { Logger.t(line).d("Dispose") }
}

inline fun <reified T> Maybe<T>.log(tag: String? = null): Maybe<T> {
    val line = tag ?: tag()
    return doOnEvent { success, error -> printEvent(line, success, error) }
        .doOnSubscribe { Logger.t(line).d("Subscribe") }
        .doOnDispose { Logger.t(line).d("Dispose") }
}

inline fun Completable.log(tag: String? = null): Completable {
    val line = tag ?: tag()
    return doOnEvent { printEvent(line, it) }
        .doOnSubscribe { Logger.t(line).d("Subscribe") }
        .doOnDispose { Logger.t(line).d("Dispose") }
}

inline fun <reified T> Observable<T>.log(tag: String? = null): Observable<T> {
    val line = tag ?: tag()
    return doOnEach { Logger.t(line).d("Each $it") }
        .doOnSubscribe { Logger.t(line).d("Subscribe") }
        .doOnDispose { Logger.t(line).d("Dispose") }
}

inline fun <reified T> Flowable<T>.log(tag: String? = null): Flowable<T> {
    val line = tag ?: tag()
    return doOnEach { Logger.t(line).d("Each $it") }
        .doOnSubscribe { Logger.t(line).d("Subscribe") }
        .doOnCancel { Logger.t(line).d("Cancel") }
}

