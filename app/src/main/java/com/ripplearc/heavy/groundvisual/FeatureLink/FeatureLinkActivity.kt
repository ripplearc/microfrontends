package com.ripplearc.heavy.groundvisual.FeatureLink

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.ripplearc.heavy.common.core.model.ViewModelFactory
import com.ripplearc.heavy.common.features.IotHistogramFeature
import com.ripplearc.heavy.common.features.IotRosterFeature
import com.ripplearc.heavy.common.features.IotTestFeature
import com.ripplearc.heavy.features.profile
import com.ripplearc.heavy.groundvisual.R
import com.ripplearc.heavy.groundvisual.appComponent
import javax.inject.Inject


/**
 * FeatureLinkActivity listens to the `android.intent.action.VIEW` and navigates to the
 * specific feature page or displays the feature list.
 */
class FeatureLinkActivity : AppCompatActivity() {

    @Inject
    lateinit var featureListViewModelProvider: ViewModelFactory<FeatureListViewModel>

    private val viewModel by lazy {
        ViewModelProviders.of(this, featureListViewModelProvider)
            .get(FeatureListViewModel::class.java)
    }

    private fun launchFeature(feature: String) {
        viewModel.getFeature(feature)?.getShortcutIntent(this)?.let(::startActivity)
    }

    private fun handleFeatureLink(uri: Uri) {
        uri.pathSegments.firstOrNull()
            ?.let(::launchFeature)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_feature_link)
        intent?.data?.let(::handleFeatureLink)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(com.ripplearc.heavy.common.features.R.menu.feature_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sceneIotRoster -> IotRosterFeature::class.profile.id
            R.id.sceneIotTest -> IotTestFeature::class.profile.id
            R.id.sceneIotHistogram -> IotHistogramFeature::class.profile.id
            else -> return false
        }.let(::launchFeature)
        return super.onOptionsItemSelected(item)
    }
}
