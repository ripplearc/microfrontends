package com.ripplearc.heavy.toolbelt.rx

import android.app.Dialog
import com.ripplearc.heavy.toolbelt.ui.CustomDateTimePicker
import io.reactivex.Observable
import java.util.*

val CustomDateTimePicker.timeObservable: Observable<Long>
    get() = Observable.create { emitter ->
        object : CustomDateTimePicker.ICustomDateTimeListener {
            override fun onSet(
                dialog: Dialog?,
                calendarSelected: Calendar?,
                dateSelected: Date?,
                year: Int,
                monthFullName: String?,
                monthShortName: String?,
                monthNumber: Int,
                date: Int,
                weekDayFullName: String?,
                weekDayShortName: String?,
                hour24: Int,
                hour12: Int,
                min: Int,
                sec: Int,
                AM_PM: String?
            ) {
                calendarSelected?.let {
                    emitter.onSafeNext(it.timeInMillis)
                }
            }

            override fun onCancel() {}
        }.let {
            setDateTimeListener(it)
        }

        emitter.setCancellable {
            setDateTimeListener(null)
        }

    }

