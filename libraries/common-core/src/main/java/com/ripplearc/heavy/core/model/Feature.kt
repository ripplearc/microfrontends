package com.ripplearc.heavy.common.core.model

import android.content.Context
import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * Feature defines the common interface to enter a feature page.
 * Type parameter D is the dependency.
 * Profile uniquely identifies the feature in navigation.
 */
interface Feature<D> {
    fun getMainEntry(): Fragment
    fun getShortcutIntent(context: Context): Intent

    data class Profile(
        val id: String,
        @IdRes val sceneId: Int
    )
}
