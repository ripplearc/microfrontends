package com.ripplearc.heavy.toolbelt.technician

import com.orhanobut.logger.LogAdapter
import dagger.Reusable
import javax.inject.Inject

/**
 * This is used to saves log messages to the disk.
 * It uses [TxtFormatStrategy] to translates text message into Txt format.
 */
@Reusable
class ExternalStorageLogAdapter @Inject constructor(private val formatStrategy: TxtFormatStrategy) :
    LogAdapter {

    override fun isLoggable(priority: Int, tag: String?): Boolean {
        val ignore = tag?.contains(ignoreTrace) ?: true
        return !ignore
    }

    override fun log(priority: Int, tag: String?, message: String) {
        formatStrategy.log(priority, tag, message)
    }

    companion object {
        val ignoreTrace = "ignore"
    }
}

