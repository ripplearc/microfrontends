package com.ripplearc.heavy.toolbelt.rx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.ripplearc.heavy.toolbelt.thread.runOnMain
import io.reactivex.*
import io.reactivex.BackpressureStrategy.*
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Convert Flowable to LiveData and handles with error with a default value.
 */
fun <T> Flowable<T>.asLiveData(onErrorJustReturn: T): LiveData<T> =
    onErrorReturnItem(onErrorJustReturn)
        .let { noErrorStream: Flowable<T> ->
            LiveDataReactiveStreams.fromPublisher(noErrorStream)
        }

/**
 * Convert Flowable to LiveData and handles with error by emitting nothing.
 */
fun <T> Flowable<T>.asLiveDataAndOnErrorReturnEmpty(): LiveData<T> =
    onErrorResumeNext(Flowable.empty())
        .let { noErrorStream: Flowable<T> ->
            LiveDataReactiveStreams.fromPublisher(noErrorStream)
        }

/**
 * Convert Observable to LiveData and handles with error with a default value.
 */
fun <T> Observable<T>.asLiveData(
    onErrorJustReturn: T,
    strategy: BackpressureStrategy = BUFFER
): LiveData<T> =
    onErrorReturnItem(onErrorJustReturn)
        ?.let { noErrorStream ->
            LiveDataReactiveStreams.fromPublisher(noErrorStream.toFlowable(strategy))
        } ?: LiveDataReactiveStreams.fromPublisher(
        Observable.just(onErrorJustReturn).toFlowable(strategy)
    )

/**
 * Convert Single to LiveData and handles with error with a default value.
 */
fun <T> Single<T>.asLiveData(onErrorJustReturn: T): LiveData<T> =
    onErrorReturnItem(onErrorJustReturn)
        ?.let { noErrorStream ->
            LiveDataReactiveStreams.fromPublisher(noErrorStream.toFlowable())
        } ?: LiveDataReactiveStreams.fromPublisher(Single.just(onErrorJustReturn).toFlowable())


/**
 * Convert Maybe to LiveData and handles with error with a default value.
 */
fun <T> Maybe<T>.asLiveData(onErrorJustReturn: T): LiveData<T> =
    onErrorReturnItem(onErrorJustReturn)
        ?.let { noErrorStream ->
            LiveDataReactiveStreams.fromPublisher(noErrorStream.toFlowable())
        } ?: LiveDataReactiveStreams.fromPublisher(Maybe.just(onErrorJustReturn).toFlowable())

/**
 * Convert Maybe to LiveData and handles with error by emitting OnComplete
 */
fun Completable.asLiveData(): LiveData<Unit> =
    onErrorComplete()
        ?.let { noErrorStream ->
            LiveDataReactiveStreams.fromPublisher<Unit>(noErrorStream.toFlowable())
        } ?: LiveDataReactiveStreams.fromPublisher(Completable.complete().toFlowable())

/**
 * Make sure observing the LiveData is called on the main thread.
 */
fun <T> LiveData<T>.observeOnMain(
    owner: LifecycleOwner,
    observer: androidx.lifecycle.Observer<in T>
) {
    let { live ->
        Runnable {
            live.observe(owner, observer)
        }.runOnMain()
    }
}
