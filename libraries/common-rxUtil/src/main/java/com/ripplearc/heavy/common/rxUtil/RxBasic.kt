package com.ripplearc.heavy.common.rxUtil

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * allow emission of true to pass through
 */
fun Observable<Boolean>.ifTrue(): Observable<Boolean> =
    filter { it }

/**
 * allow emission of false to pass through
 */
fun Observable<Boolean>.ifFalse(): Observable<Boolean> =
    filter { !it }

/**
 * allow emission of true to pass through
 */
fun Single<Boolean>.ifTrue(): Maybe<Boolean> =
    filter { it }

/**
 * allow emission of false to pass through
 */
fun Single<Boolean>.ifFalse(): Maybe<Boolean> =
    filter { !it }

/**
 * Ignore null value from the mapping transform
 *
 * @param T type of the input for the transform function
 * @param R type of the return value for the transform function
 * @param transform transform function to convert a Single to a Maybe
 * @return a Maybe converted from Single, and may not have a value if the single
 * emission maps to null
 */
fun <T, R : Any> Single<T>.mapNotNull(transform: (T) -> R?): Maybe<R> =
    flatMapMaybe { value ->
        Maybe.fromCallable {
            transform(value)
        }
    }

/**
 * Ignore the null transform from the mapping transform
 *
 * @param T type of the input for the transform function
 * @param R type of the return value for the transform function
 * @param transform transform function that many return a null value
 * @return A Flowable with non-null emission
 */
fun <T, R : Any> Flowable<T>.mapNotNull(transform: (T) -> R?): Flowable<R> =
    flatMapIterable { value ->
        listOfNotNull(transform(value))
    }

/**
 * Ignore the null transform from the mapping transform
 *
 * @param T type of the input for the transform function
 * @param R type of the return value for the transform function
 * @param transform transform function that may return a null value
 * @return An Observable with non-null emission
 */
fun <T, R : Any> Observable<T>.mapNotNull(transform: (T) -> R?): Observable<R> =
    flatMapIterable { value ->
        listOfNotNull(transform(value))
    }

/**
 * Start with a default value if the default value is not null
 *
 * @param T type of the Observable and the default value
 * @param value the default value that may be null
 * @return an Observable that adds a default value to the beginning of the source,
 * if the default value is not null
 */
fun <T : Any> Observable<T>.startWithNotNull(value: T?): Observable<T> =
    value?.let {
        startWithArray(it)
    } ?: startWithArray()

