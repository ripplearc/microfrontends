package com.ripplearc.heavy.groundvisual.FeatureLink

import androidx.lifecycle.ViewModel
import com.ripplearc.heavy.groundvisual.viewmodel.FeatureNavigation
import com.ripplearc.heavy.groundvisual.viewmodel.FeatureNavigationViewModel
import javax.inject.Inject

/**
 * FeatureListViewModel is the viewmodel of FeatureList Activity.
 * It navigates the feature page based on the `android.intent.action.VIEW` or allows the
 * use to select the feature page from the menu.
 */
internal class FeatureListViewModel @Inject constructor(private val navigationViewModel: FeatureNavigationViewModel) :
    FeatureNavigation by navigationViewModel, ViewModel()
