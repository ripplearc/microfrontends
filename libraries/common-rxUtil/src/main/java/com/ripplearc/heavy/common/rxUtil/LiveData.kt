package com.ripplearc.heavy.common.rxUtil

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.*


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
    strategy: BackpressureStrategy = BackpressureStrategy.BUFFER
): LiveData<T> =
    onErrorReturnItem(onErrorJustReturn)
        ?.let { noErrorStream ->
            LiveDataReactiveStreams.fromPublisher(noErrorStream.toFlowable(strategy))
        } ?: LiveDataReactiveStreams.fromPublisher(
        Observable.just(onErrorJustReturn).toFlowable(strategy)
    )

/**
 * Convert Observable to LiveData and handles with error with complete event
 */
fun <T> Observable<T>.asLiveDataOnErrorReturnEmpty(
    strategy: BackpressureStrategy = BackpressureStrategy.BUFFER
): LiveData<T> =
    onErrorResumeNext(Observable.empty())
        ?.let { noErrorStream ->
            LiveDataReactiveStreams.fromPublisher(noErrorStream.toFlowable(strategy))
        } ?: LiveDataReactiveStreams.fromPublisher(
        Observable.empty<T>().toFlowable(strategy)
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

fun Runnable.runOnMain() {
    run {
        Handler(Looper.getMainLooper()).post(this)
    }
}
