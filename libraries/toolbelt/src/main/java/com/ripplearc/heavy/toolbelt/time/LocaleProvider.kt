package com.ripplearc.heavy.toolbelt.time

import dagger.Reusable
import java.util.*
import javax.inject.Inject

@Reusable
class LocaleProvider @Inject constructor() {
    val locale: Locale
        get() = Locale.US
}
