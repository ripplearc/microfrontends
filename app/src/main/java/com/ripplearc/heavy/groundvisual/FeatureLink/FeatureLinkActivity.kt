package com.ripplearc.heavy.groundvisual.FeatureLink

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ripplearc.heavy.common.core.model.ViewModelFactory
import com.ripplearc.heavy.groundvisual.R
import com.ripplearc.heavy.groundvisual.appComponent

import kotlinx.android.synthetic.main.activity_feature_link.*
import javax.inject.Inject

class FeatureLinkActivity : AppCompatActivity() {

    @Inject
    lateinit var featureListViewModelProvider: ViewModelFactory<FeatureListViewModel>

    private val viewModel by lazy {
        ViewModelProviders.of(this, featureListViewModelProvider)
            .get(FeatureListViewModel::class.java)
    }

    private fun launchFeature(feature: String) {
        viewModel.getFeature(feature)?.getShortcutIntent(this).let(::startActivity)
        finish()
    }

    private fun handleFeatureLink(uri: Uri) {
        uri.pathSegments.firstOrNull()
            ?.let(::launchFeature) ?: finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_feature_link)
        intent?.data?.let(::handleFeatureLink) ?: finish()
    }

}
