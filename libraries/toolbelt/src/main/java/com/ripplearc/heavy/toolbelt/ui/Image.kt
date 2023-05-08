package com.ripplearc.heavy.toolbelt.ui

import android.media.Image

fun Image.extractData() =
    planes.getOrNull(0)?.buffer?.let { buffer ->
        ByteArray(buffer.remaining()).apply {
            buffer.get(this)
        }
    }
