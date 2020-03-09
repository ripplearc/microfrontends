package com.ripplearc.heavy.iot.test.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.ripplearc.heavy.common.core.model.ViewModelFactory
import com.ripplearc.heavy.common.rxUtil.*

import com.ripplearc.heavy.iot.test.R
import com.ripplearc.heavy.iot.test.feature.iotTestComponent
import kotlinx.android.synthetic.main.request_fragment.*
import kotlinx.coroutines.*
import javax.inject.Inject

class RequestFragment : Fragment() {

    companion object {
        fun newInstance() = RequestFragment()
    }

    @Inject
    lateinit var rosterViewModelProvider: ViewModelFactory<RequestViewModel>

    @Inject
    lateinit var coroutinesContext: ExecutorCoroutineDispatcher

    private val viewModel by lazy {
        ViewModelProviders.of(this, rosterViewModelProvider).get(RequestViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.request_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iotTestComponent.inject(this)
        lifecycleScope.launch(coroutinesContext) {
            actionBind()
            dataBind()
        }
    }

    private fun actionBind() {
        RxView.clicks(publish_button)
            .map { topic_bar.text.toString() }
            .doOnNext(::animatePublish)
            .bind(viewModel.publishTopicRelay)
            .disposeOnStop(viewLifecycleOwner)

        RxTextView.textChanges(message_box)
            .map { it.toString() }
            .bind(viewModel.messageRelay)
            .disposeOnStop(viewLifecycleOwner)
    }

    private fun animatePublish(text: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            topic_bar.setText("*** Publish ***")
            delay(200)
            topic_bar.setText("-** Publish ***")
            delay(200)
            topic_bar.setText("--* Publish ***")
            delay(200)
            topic_bar.setText("--- Publish ***")
            delay(200)
            topic_bar.setText("--- Publish -**")
            delay(200)
            topic_bar.setText("--- Publish --*")
            delay(200)
            topic_bar.setText("*** Publish ***")
            delay(200)
            topic_bar.setText(text)
        }
    }

    private fun dataBind() {
        viewModel.topicObservable
            .asLiveData("No Device Selected")
            .observeOnMain(viewLifecycleOwner, Observer {
                it?.let { topic_bar.setText(it) }
            })

        viewModel.messageObservable
            .asLiveData("No Device Selected")
            .observeOnMain(viewLifecycleOwner, Observer {
                it?.let { message_box.setText(it) }
            })

        viewModel.listenToMessages()
            .asLiveData(onErrorJustReturn = "Fail to receive message.")
            .observeOnMain(viewLifecycleOwner, Observer {})

        viewModel.publishTopic()
            .asLiveData()
            .observeOnMain(viewLifecycleOwner, Observer {})
    }
}
