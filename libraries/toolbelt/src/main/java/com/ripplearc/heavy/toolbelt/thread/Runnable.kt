package com.ripplearc.heavy.toolbelt.thread

import android.os.Handler
import android.os.Looper

fun Runnable.runOnBackground() {
    Thread(this).start()
}

fun Runnable.runOnMain() {
    run {
        Handler(Looper.getMainLooper()).post(this)
    }
}