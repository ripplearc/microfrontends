package com.ripplearc.heavy.iot_roster.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ripplearc.heavy.common_core.model.ViewModelFactory
import com.ripplearc.heavy.iot_roster.R
import com.ripplearc.heavy.iot_roster.feature.iotRosterComponent
import javax.inject.Inject

class RosterSpinnerFragment : Fragment() {

    companion object {
        fun newInstance() = RosterSpinnerFragment()
    }

    @Inject
    lateinit var rosterViewModelProvider: ViewModelFactory<RosterSpinnerViewModel>

    private val viewModel by lazy {
        ViewModelProviders.of(this, rosterViewModelProvider).get(RosterSpinnerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.roster_spinner_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iotRosterComponent.inject(this)

        view?.findViewById<Spinner>(R.id.device_roster)?.adapter = viewModel.spinnerAdapter
    }

}
