package com.ripplearc.heavy.toolbelt.service

import android.app.Service
import android.os.IBinder

/**
 * Provide the Service through the Binder instance
 */
interface IServiceBinder: IBinder {
    fun getService(): Service
}
