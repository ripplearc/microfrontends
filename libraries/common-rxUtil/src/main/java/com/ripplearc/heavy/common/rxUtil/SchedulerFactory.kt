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

	/**
	 * instance of IO scheduler
	 *
	 */
    fun io() =
        Schedulers.io()

	/**
	 * instance of Computation scheduler
	 *
	 */
    fun computation() =
        Schedulers.computation()

	/**
	 * instance of Main scheduler
	 *
	 */
    fun main(): Scheduler =
        AndroidSchedulers.mainThread()

	/**
	 * instance of Single scheduler where to carry out Single scheduler
	 *
	 */
    fun threadSafe() =
        Schedulers.single()

	/**
	 * instance of a scheduler running on a new scheduler, this is for task
	 * that has to run on its own thread
	 *
	 */
    fun exclusive() =
        Schedulers.newThread()
}
