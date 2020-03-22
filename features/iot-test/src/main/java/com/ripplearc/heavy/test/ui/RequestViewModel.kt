package com.ripplearc.heavy.iot.test.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.ripplearc.heavy.common.rxUtil.SchedulerFactory
import com.ripplearc.heavy.test.controller.MessageListener
import com.ripplearc.heavy.test.controller.MessageSender
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RequestViewModel @Inject internal constructor(
    private val context: Context,
    private val messageSender: MessageSender,
    private val messageListener: MessageListener,
    private val schedulerFactory: SchedulerFactory
) : ViewModel() {

    val sentTopicObservable: Observable<String> =
        messageSender.sentTopicObservable

    val sentMessageObservable: Observable<String> =
        messageSender.sentMessageObservable

    internal fun listenToMessages() = messageListener.listen()

    fun publishTopic(): Completable =
        messageSender.publishTopic()
            .observeOn(schedulerFactory.main())
            .doOnError {
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
}
