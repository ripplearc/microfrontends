package com.ripplearc.heavy.common.rxUtil

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.reactivex.Observable

enum class NetworkState {
    CONNECTED,
    DISCONNECTED
}

/**
 * An observable that emits `Connected` when the either Wifi or Cellular is available.
 * emits `Disconnected` when both of them are lost.
 *
 * @param context ApplicationContext
 */
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
            val builder = NetworkRequest.Builder()
            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

            val networkRequest = builder.build()

            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .registerNetworkCallback(networkRequest, callback)
            emitter.setCancellable {
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                    .unregisterNetworkCallback(callback)
            }
        }
    }
