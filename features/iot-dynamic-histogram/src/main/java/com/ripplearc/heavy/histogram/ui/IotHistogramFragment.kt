package com.ripplearc.heavy.iot.dynamic.histogram.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ripplearc.heavy.common.core.model.ViewModelFactory

import com.ripplearc.heavy.iot.dynamic.histogram.R
import com.ripplearc.heavy.iot.dynamic.histogram.feature.iotHistogramComponent
import javax.inject.Inject

class IotHistogramFragment : Fragment() {

    companion object {
        fun newInstance() = IotHistogramFragment()
    }

    @Inject
    lateinit var histogramViewModelProvider: ViewModelFactory<IotHistogramViewModel>

    private val viewModel by lazy {
        ViewModelProvider(this, histogramViewModelProvider)
            .get(IotHistogramViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.iot_histogram_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iotHistogramComponent.inject(this)
        viewModel
        // TODO: Use the ViewModel
    }

}
