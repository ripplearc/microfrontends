package com.ripplearc.heavy.iot.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.ripplearc.heavy.common.core.model.ViewModelFactory
import com.ripplearc.heavy.common.coroutines.CoroutinesContextProvider
import com.ripplearc.heavy.common.rxUtil.*
import com.ripplearc.heavy.iot.test.R
import com.ripplearc.heavy.iot.test.feature.iotTestComponent
import com.ripplearc.heavy.test.model.MessageItem
import com.ripplearc.heavy.test.ui.receive.MessageRecyclerViewAdapter
import kotlinx.android.synthetic.main.request_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestFragment : Fragment() {

    companion object {
        fun newInstance() = RequestFragment()
    }

    @Inject
    lateinit var rosterViewModelProvider: ViewModelFactory<RequestViewModel>

    @Inject
    lateinit var coroutinesContextProvider: CoroutinesContextProvider

    private val viewModel by lazy {
        ViewModelProvider(this, rosterViewModelProvider).get(RequestViewModel::class.java)
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
        lifecycleScope.launch(coroutinesContextProvider.computation) {
            actionBind()
            dataBind()
        }
    }

    private fun actionBind() {
        RxView.clicks(publish_button)
            .map { topic_bar.text.toString() }
            .doOnNext(::animatePublish)
            .flatLiveBindDelayError(viewLifecycleOwner, viewModel::publishTopic)

    }

    private fun dataBind() {
        viewModel.sentTopicObservable
            .asLiveData("No Device Selected")
            .observeOnMain(viewLifecycleOwner, Observer { topic ->
                topic?.let { topic_bar.setText(it) }
            })

        viewModel.sentMessageObservable
            .asLiveData("No Device Selected")
            .observeOnMain(viewLifecycleOwner, Observer { message ->
                message?.let { message_box.setText(it) }
            })

        viewModel.listenToMessages()
            .asLiveDataAndOnErrorReturnEmpty()
            .observeOnMain(viewLifecycleOwner, Observer { messages ->
                messages?.let {
                    recycler_view?.run {
                        (adapter as? MessageRecyclerViewAdapter)?.updateMessageItems(it)
                        layoutManager?.scrollToPosition(0)
                    }
                }
            })

        lifecycleScope.launch(coroutinesContextProvider.main) {
            recycler_view?.apply {
                layoutManager = LinearLayoutManager(this@RequestFragment.context)
                adapter = MessageRecyclerViewAdapter(listOf(MessageItem("Test")))
            }
        }
    }

    private fun animatePublish(text: String) {
        lifecycleScope.launch(coroutinesContextProvider.main) {
            publish_button.isEnabled = false
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
            publish_button.isEnabled = true
        }
    }

}
