package com.ripplearc.heavy.toolbelt.time

import com.ripplearc.heavy.toolbelt.constants.DateFormat
import java.text.SimpleDateFormat
import java.util.*

val Long.toDateString: String
    get() {
        val date = Date(this)
        val formatter = SimpleDateFormat(DateFormat.DISPLAY_DATE_TIME_FORMAT)
        return formatter.format(date)
    }
