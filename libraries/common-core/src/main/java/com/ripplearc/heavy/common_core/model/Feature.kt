package com.ripplearc.heavy.common_core.model

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

interface Feature<D : Dependencies> {
    fun getMainEntry(): Fragment
    fun getShortcutIntent(context: Context): Intent

}
