package com.ripplearc.heavy.toolbelt.rx

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

fun Observable<Boolean>.ifTrue(): Observable<Boolean> =
    filter { it }

fun Observable<Boolean>.ifFalse(): Observable<Boolean> =
    filter { !it }

fun Single<Boolean>.ifTrue(): Maybe<Boolean> =
    filter { it }

fun Single<Boolean>.ifFalse(): Maybe<Boolean> =
    filter { !it }

fun <T, R : Any> Single<T>.mapNotNull(transform: (T) -> R?): Maybe<R> =
    flatMapMaybe { value ->
        Maybe.fromCallable {
            transform(value)
        }
    }

fun <T, R : Any> Flowable<T>.mapNotNull(transform: (T) -> R?): Flowable<R> =
    flatMapIterable { value ->
        listOfNotNull(transform(value))
    }

fun <T, R : Any> Observable<T>.mapNotNull(transform: (T) -> R?): Observable<R> =
    flatMapIterable { value ->
        listOfNotNull(transform(value))
    }

fun <T : Any> Observable<T>.startWithNotNull(value: T?): Observable<T> =
    value?.let {
        startWithArray(it)
    } ?: startWithArray()

