package com.ripplearc.heavy.common.rxUtil

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Completable
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
    .observeOnMain(lifeCycleOwner, Observer(target::accept))

inline fun <reified T> Observable<T>.switchLiveBind(
    lifeCycleOwner: LifecycleOwner,
    noinline target: (T) -> Completable
) = this.switchMapCompletable(target)
    .asLiveData()
    .observeOnMain(lifeCycleOwner, Observer {})

inline fun <reified T> Observable<T>.switchLiveBindDelayError(
    lifeCycleOwner: LifecycleOwner,
    noinline target: (T) -> Completable
) = this.switchMapCompletableDelayError(target)
    .asLiveData()
    .observeOnMain(lifeCycleOwner, Observer {})

inline fun <reified T> Observable<T>.flatLiveBind(
    lifeCycleOwner: LifecycleOwner,
    noinline target: (T) -> Completable
) = this.flatMapCompletable(target)
    .asLiveData()
    .observeOnMain(lifeCycleOwner, Observer {})

inline fun <reified T> Observable<T>.flatLiveBindDelayError(
    lifeCycleOwner: LifecycleOwner,
    noinline target: (T) -> Completable
) = this.flatMapCompletable {
        target(it).onErrorComplete()
    }
    .asLiveData()
    .observeOnMain(lifeCycleOwner, Observer {})

inline fun <reified T> Observable<T>.flatLiveBindDelayError(
    lifeCycleOwner: LifecycleOwner,
    noinline target: () -> Completable
) = this.flatMapCompletable {
        target().onErrorComplete()
    }
    .asLiveData()
    .observeOnMain(lifeCycleOwner, Observer {})

