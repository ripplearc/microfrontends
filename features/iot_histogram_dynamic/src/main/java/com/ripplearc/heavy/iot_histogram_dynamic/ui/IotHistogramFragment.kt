package com.ripplearc.heavy.iot_histogram_dynamic.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ripplearc.heavy.iot_histogram_dynamic.R

class IotHistogramFragment : Fragment() {

    companion object {
        fun newInstance() = IotHistogramFragment()
    }

    private lateinit var viewModel: IotHistogramViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.iot_histogram_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(IotHistogramViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
