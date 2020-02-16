package com.ripplearc.heavy.iot.roster.ui

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.ripplearc.heavy.iot.roster.R
import javax.inject.Inject

class RosterSpinnerViewModel @Inject constructor(context: Context) : ViewModel() {
    val spinnerAdapter = ArrayAdapter.createFromResource(
        context,
        R.array.planets_array,
        R.layout.spinner_item
    ).also { adapter ->
        adapter.setDropDownViewResource(R.layout.spinner_item)
    }
}
