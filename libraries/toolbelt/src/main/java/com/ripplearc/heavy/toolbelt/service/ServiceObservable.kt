package com.ripplearc.heavy.toolbelt.service

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.ripplearc.heavy.toolbelt.rx.onSafeNext
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 * bindServiceObservable calls bindService upon subscribe, and emits the Service when the connection is established.
 * It calls unbindService upon cancel.
 */
inline fun <reified T : Service, reified B : IServiceBinder> Context.bindServiceObservable(): Observable<T> =
    Observable.create<T> { emitter: ObservableEmitter<T> ->
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                (service as? B)?.also { binder ->
                    if (emitter.isDisposed) return
                    emitter.onSafeNext(binder.getService() as T)
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                if (emitter.isDisposed) return
                emitter.onComplete()
            }
        }.also {
            Intent(this, T::class.java).also { intent ->
                bindService(intent, it, Context.BIND_AUTO_CREATE)
            }
            emitter.setCancellable {
                unbindService(it)
            }
        }
    }