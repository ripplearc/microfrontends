package com.ripplearc.heavy.common.rxUtil

import io.reactivex.Observable

fun Observable<*>.onErrorComplete(): Observable<*> = onErrorResumeNext(Observable.empty())


