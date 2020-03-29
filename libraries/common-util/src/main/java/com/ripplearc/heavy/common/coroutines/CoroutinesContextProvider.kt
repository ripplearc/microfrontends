package com.ripplearc.heavy.common.coroutines

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutines context provider for dependence injection purpose.
 */
@Reusable
class CoroutinesContextProvider @Inject constructor() {
    val main: CoroutineContext by lazy { Dispatchers.Main }
    val io: CoroutineContext by lazy { Dispatchers.IO }
    val computation: CoroutineContext by lazy { Dispatchers.Default }
}