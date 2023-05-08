/*
 * Copyright 2016-2018 David Karnok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.ripplearc.heavy.toolbelt.rx

import io.reactivex.Flowable
import org.reactivestreams.Subscriber


/**
 * Convert a V1 Observable into a V2 Flowable, composing backpressure and cancellation.
 *
 * @param <T> the value type
 */
class ObservableV1ToFlowable<T> constructor(private val source: rx.Observable<T>) :
    Flowable<T>() {
    override fun subscribeActual(s: Subscriber<in T>?) {
        s?.let { subscriber ->
            ObservableSubscriber(subscriber).let { parent ->
                ObservableSubscriberSubscription(parent).let { subscription ->
                    subscriber.onSubscribe(subscription)
                    source.unsafeSubscribe(parent)
                }
            }
        }
    }

    class ObservableSubscriber<T> constructor(private val actual: Subscriber<in T>) :
        rx.Subscriber<T>() {

        private var done = false

        init {
            request(0)
        }

        override fun onNext(t: T) {
            if (done)
                return
            actual.onNext(t)
        }

        override fun onCompleted() {
            if (done)
                return
            done = true
            actual.onComplete()
            unsubscribe()
        }

        override fun onError(e: Throwable?) {
            if (done) {
                return
            }
            done = true
            actual.onError(e)
            unsubscribe()
        }

        fun requestMore(n: Long) {
            request(n)
        }
    }

    class ObservableSubscriberSubscription<T> constructor(private val parent: ObservableSubscriber<T>) :
        org.reactivestreams.Subscription {
        override fun cancel() {
            parent.unsubscribe()
        }

        override fun request(n: Long) {
            parent.requestMore(n)
        }

    }
}

