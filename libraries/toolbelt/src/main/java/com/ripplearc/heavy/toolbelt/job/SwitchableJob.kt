package com.ripplearc.heavy.toolbelt.job

import io.reactivex.Flowable
import io.reactivex.Observable

interface SwitchableJob<T, R> {
    fun startJobFlowable(switch: Observable<T>): Flowable<R>
}

