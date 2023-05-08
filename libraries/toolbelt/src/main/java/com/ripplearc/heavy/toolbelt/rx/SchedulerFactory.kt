package com.ripplearc.heavy.toolbelt.rx

import android.os.Handler
import android.os.HandlerThread
import dagger.Reusable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.ComputationScheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Factory that produces scheduler for different purposes
 */
@Reusable
class SchedulerFactory @Inject constructor() {
    fun makeHandlerScheduler(): Scheduler {
        var backgroundHandler: Handler

        HandlerThread("Camera Capture Request").also {
            it.start()
            backgroundHandler = Handler(it.looper)
        }
        return AndroidSchedulers.from(backgroundHandler.looper)
    }

    fun makeIOScheduler() =
        Schedulers.io()

    fun makeComputationScheduler() =
        Schedulers.computation()

    fun makeMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    fun makeSequentialScheduler() =
        Schedulers.single()

    fun makeExclusiveScheduler() =
        Schedulers.newThread()
}