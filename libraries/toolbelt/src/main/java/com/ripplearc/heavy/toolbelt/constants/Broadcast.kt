package com.ripplearc.heavy.toolbelt.constants

interface Broadcast {
    companion object {
        val DEVICE_ROTATION: String
            get() = "intent.broadcast.device.rotation"

        val ROTATION: String
            get() = "intent.extra.rotation"
    }
}