package com.ripplearc.heavy.toolbelt.value

import io.realm.RealmList
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import org.nield.kotlinstatistics.median

val <T> Array<T>.realmList: RealmList<T>
    get() {
        val list = RealmList<T>()
        forEach { list.add(it) }
        return list
    }

val FloatArray.squareRoot
    get() = sqrt(map { it.pow(2) }.sum())

fun FloatArray.stringOfElement(): String {
    return fold("", { slice, element ->
        "$slice $element"
    })
}

val List<FloatArray>.medianOfXyz: FloatArray
    get() = intArrayOf(0, 1, 2)
        .map { median(this, it) }
        .toFloatArray()

private fun median(samples: List<FloatArray>, index: Int) =
    samples
        .mapNotNull { it.getOrNull(index) }
        .map { abs(it) }
        .median()
        .toFloat()
