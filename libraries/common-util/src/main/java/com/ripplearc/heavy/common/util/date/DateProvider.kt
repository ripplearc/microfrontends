package com.ripplearc.heavy.common.util.date

import dagger.Reusable
import java.util.*
import javax.inject.Inject

/**
 * Date provider for dependence injection purpose.
 */
@Reusable
class DateProvider @Inject constructor() {
    val date: Date
        get() = Date()
}