package com.ripplearc.heavy.common_features

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.ripplearc.heavy.common_core.model.Dependencies


interface CommonDependencies : Dependencies {
    fun applicationContext(): Context
}

interface RadioDependencies: Dependencies {

}

