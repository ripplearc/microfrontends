package com.ripplearc.heavy.iot_roster.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ripplearc.heavy.iot_roster.R

class RosterSpinnerFragment : Fragment() {

    companion object {
        fun newInstance() = RosterSpinnerFragment()
    }

    private lateinit var viewModel: RosterSpinnerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.roster_spinner_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RosterSpinnerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
