package com.ripplearc.heavy.toolbelt.functional

inline fun <T> Iterable<T>.doOnNext(action: (T) -> Unit): Iterable<T> {
    for (element in this) {
        action(element)
    }
    return this
}
