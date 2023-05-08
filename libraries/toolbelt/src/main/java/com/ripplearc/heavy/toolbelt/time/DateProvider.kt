package com.ripplearc.heavy.toolbelt.time

import dagger.Reusable
import java.util.*
import javax.inject.Inject

@Reusable
class DateProvider @Inject constructor() {
	val date: Date
		get() = Date()
}

fun Date.toFormatString(formatter: String): String =
	android.text.format.DateFormat.format(formatter, this).toString()
