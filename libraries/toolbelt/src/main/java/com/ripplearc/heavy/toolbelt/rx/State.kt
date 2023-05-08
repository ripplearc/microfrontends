package com.ripplearc.heavy.toolbelt.rx

import io.reactivex.Flowable
import io.reactivex.Observable

fun <T : Any> Flowable<T>.enumerated(): Flowable<Pair<Int, T>> =
    scan(Pair<Int, T?>(0, null)) { slice, newValue ->
        Pair(slice.first + 1, newValue)
    }.flatMapIterable { (index, value) ->
        listOfNotNull(index).zip(listOfNotNull(value))
    }

fun <T : Any> Observable<T>.enumerated(): Observable<Pair<Int, T>> =
    scan(Pair<Int, T?>(0, null)) { slice, newValue ->
        Pair(slice.first + 1, newValue)
    }.flatMapIterable { (index, value) ->
        listOfNotNull(index).zip(listOfNotNull(value))
    }
