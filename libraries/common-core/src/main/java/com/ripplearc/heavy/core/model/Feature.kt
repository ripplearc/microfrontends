package com.ripplearc.heavy.common.core.model

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

interface Feature<D> {
    fun getMainEntry(): Fragment
    fun getShortcutIntent(context: Context): Intent
}
