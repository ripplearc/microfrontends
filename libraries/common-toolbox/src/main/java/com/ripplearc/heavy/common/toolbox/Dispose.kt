package com.ripplearc.heavy.common.toolbox


import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

private class LifecycleDisposable(obj: Disposable) : DefaultLifecycleObserver, Disposable by obj {
    override fun onStop(owner: LifecycleOwner) {
        if (!isDisposed) {
            synchronized(this) {
                if (!isDisposed)
                    dispose()
            }
        }
    }
}


/**
 * Dispose the Disposable when the owner that holds the Disposable is stopped.
 */
fun Disposable.disposeOnStop(owner: LifecycleOwner) {
    owner.lifecycle.addObserver(LifecycleDisposable(this))
}

/**
 * Dispose the Disposable to the Composite collector.
 */
fun Disposable.disposedBy(disposables: CompositeDisposable) {
    disposables.add(this)
}
