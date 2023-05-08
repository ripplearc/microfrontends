package com.ripplearc.heavy.toolbelt.rx

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject
import rx.Subscription

/**
 * Forward the onNext event of ReplaySubject to the target ReplaySubject.
 */
inline fun <reified T> ReplaySubject<T>.bind(forwardTarget: ReplaySubject<T>): Disposable {
    return subscribe { forwardTarget.onNext(it) }
}

/**
 * Forward the onNext event of v1 Observable to the target ReplaySubject.
 */
inline fun <reified T> Observable<T>.bind(forwardTarget: ReplaySubject<T>): Disposable {
    return this.subscribe { forwardTarget.onNext(it) }
}

/**
 * Forward the onNext event of v2 Observable to the target ReplaySubject.
 */
inline fun <reified T> rx.Observable<T>.bind(forwardTarget: ReplaySubject<T>): Subscription {
    return this.subscribe { forwardTarget.onNext(it) }
}

/**
 * Forward the onNext event of Flowable to the target BehaviorSubject.
 */
inline fun <reified T> Flowable<T>.bind(forwardTarget: BehaviorSubject<T>): Disposable {
    return this.subscribe { forwardTarget.onNext(it) }
}
