package com.ripplearc.heavy.common.util.date

import dagger.Reusable
import java.util.*
import javax.inject.Inject

@Reusable
class DateProvider @Inject constructor() {
    val date: Date
        get() = Date()
}