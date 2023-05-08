package com.ripplearc.heavy.toolbelt.rx

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import io.reactivex.Observable

enum class NetworkState {
    CONNECTED,
    DISCONNECTED
}

fun connectivityObservable(context: Context): Observable<NetworkState> =
    Observable.create { emitter ->
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                emitter.onSafeNext(NetworkState.CONNECTED)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                emitter.onSafeNext(NetworkState.DISCONNECTED)
            }
        }.let { callback ->
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .registerDefaultNetworkCallback(callback)

            emitter.setCancellable {

                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                    .unregisterNetworkCallback(callback)
            }
        }

    }
