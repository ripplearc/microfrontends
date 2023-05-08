package com.ripplearc.heavy.common.rxUtil

import io.reactivex.Observable

/**
 * Ignore the error and returns complete. Similar to Completable.onErrorComplete()
 *
 * @return An observable that returns complete when error happens.
 */
fun Observable<*>.onErrorComplete(): Observable<*> = onErrorResumeNext(Observable.empty())


