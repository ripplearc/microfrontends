package com.ripplearc.heavy.toolbelt.value

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.round(places: Int): Double {
    val scale = 10.0.pow(places.toDouble())
    return (this * scale).roundToInt() / scale
}