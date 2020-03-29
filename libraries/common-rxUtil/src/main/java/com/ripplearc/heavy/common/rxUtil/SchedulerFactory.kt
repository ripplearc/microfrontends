package com.ripplearc.heavy.common.rxUtil

import android.os.Handler
import android.os.HandlerThread
import dagger.Reusable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Factory that produces scheduler for different purposes
 */
@Reusable
class SchedulerFactory @Inject constructor() {
    fun makeHandlerScheduler(): Scheduler {
        var backgroundHandler: Handler

        HandlerThread("Handler of Scheduler Factory").also {
            it.start()
            backgroundHandler = Handler(it.looper)
        }
        return AndroidSchedulers.from(backgroundHandler.looper)
    }

    fun io() =
        Schedulers.io()

    fun computation() =
        Schedulers.computation()

    fun main(): Scheduler =
        AndroidSchedulers.mainThread()

    fun threadSafe() =
        Schedulers.single()

    fun exclusive() =
        Schedulers.newThread()
}