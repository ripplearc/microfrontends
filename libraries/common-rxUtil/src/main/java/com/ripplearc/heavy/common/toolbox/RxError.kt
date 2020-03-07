package com.ripplearc.heavy.common.toolbox

import io.reactivex.Observable
import io.reactivex.Single

fun Observable<*>.ignoreError(): Observable<*> = onErrorResumeNext(Observable.empty())
fun Single<*>.ignoreError(): Observable<*> = onErrorResumeNext(Observable.empty())
