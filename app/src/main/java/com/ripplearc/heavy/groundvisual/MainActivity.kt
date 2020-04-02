package com.ripplearc.heavy.groundvisual

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ripplearc.heavy.common.features.platform.IotHistogramFeature
import com.ripplearc.heavy.common.features.platform.IotRosterFeature
import com.ripplearc.heavy.common.features.platform.IotTestFeature
import com.ripplearc.heavy.common.features.platform.profile
import com.ripplearc.heavy.common.features.manager.FeatureManager
import com.ripplearc.heavy.common.features.manager.getFeature
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var featureManager: FeatureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(this)

        loadRoster()
        loadPlayground()
        loadDynamicHistogram()
    }

    private fun loadRoster() =
        featureManager.getFeature<IotRosterFeature, IotRosterFeature.Dependencies>(appComponent)
            ?.getMainEntry()?.load(IotRosterFeature::class.profile.id, R.id.roster_fragment)

    private fun loadPlayground() =
        featureManager.getFeature<IotTestFeature, IotTestFeature.Dependencies>(appComponent)
            ?.getMainEntry()?.load(IotTestFeature::class.profile.id, R.id.playground_fragment)

    private fun loadDynamicHistogram() =
        featureManager.getFeature<IotHistogramFeature, IotHistogramFeature.Dependencies>(
            appComponent
        )?.getMainEntry()?.load(IotHistogramFeature::class.profile.id, R.id.histogram_fragment)

    private fun Fragment.load(tag: String, @IdRes containerId: Int) =
        with(supportFragmentManager.beginTransaction()) {
            val displayFragment = supportFragmentManager.findFragmentByTag(tag)

            this.add(containerId, this@load, tag)
            if (displayFragment != null) {
                hide(displayFragment).commit()
                supportFragmentManager.beginTransaction().remove(displayFragment).commit()
            } else {
                commit()
            }
        }

}
