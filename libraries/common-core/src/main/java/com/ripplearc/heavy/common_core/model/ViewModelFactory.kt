package com.ripplearc.heavy.common_core.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory<VM : ViewModel> @Inject constructor(
    private val viewModel: Provider<VM>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel.get() as T
}