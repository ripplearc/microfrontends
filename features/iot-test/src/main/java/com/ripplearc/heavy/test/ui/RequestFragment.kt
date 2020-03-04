package com.ripplearc.heavy.iot.test.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ripplearc.heavy.common.core.model.ViewModelFactory

import com.ripplearc.heavy.iot.test.R
import com.ripplearc.heavy.iot.test.feature.iotTestComponent
import com.ripplearc.heavy.toolbelt.constants.Emoji
import com.ripplearc.heavy.toolbelt.rx.asLiveData
import com.ripplearc.heavy.toolbelt.rx.log
import com.ripplearc.heavy.toolbelt.rx.observeOnMain
import kotlinx.android.synthetic.main.request_fragment.*
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.launch
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
            dataBind()
        }
    }

    private fun dataBind() {
        viewModel.topicObservable
            .log(Emoji.CellPhone)
            .asLiveData("No Device Selected")
            .observeOnMain(viewLifecycleOwner, Observer {
                it?.let { topic_bar.setText(it) }
            })
    }
}
