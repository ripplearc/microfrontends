package com.ripplearc.heavy.common.toolbox

import com.jakewharton.rxrelay2.Relay
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject

/**
 * Forward the onNext event of v2 Observable to the target ReplaySubject.
 */
inline fun <reified T> Observable<T>.bind(target: Relay<T>): Disposable {
    return this
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe { target.accept(it) }
}

