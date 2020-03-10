package com.ripplearc.heavy.common.rxUtil

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Forward the onNext event of v2 Observable to the target ReplaySubject.
 */
inline fun <reified T> Observable<T>.bind(target: Relay<T>): Disposable {
    return this
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe { target.accept(it) }
}

/**
 * Forward the onNext event of v2 Observable to the target ReplaySubject.
 */
inline fun <reified T> Observable<T>.liveBind(
    lifeCycleOwner: LifecycleOwner,
    target: Relay<T>
) = this.asLiveDataOnErrorReturnEmpty()
    .observeOnMain(lifeCycleOwner, Observer {
        target.accept(it)
    })
