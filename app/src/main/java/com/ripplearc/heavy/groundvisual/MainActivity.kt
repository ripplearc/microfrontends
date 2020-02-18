package com.ripplearc.heavy.groundvisual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ripplearc.heavy.common.features.*
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

    private fun loadRoster() {
        with(supportFragmentManager.beginTransaction()) {
            val TAG_TOP_FRAGMENT = "top"
            val displayFragment = supportFragmentManager.findFragmentByTag(TAG_TOP_FRAGMENT)
            val featureFragment =
                featureManager.getFeature<IotRosterFeature, IotRosterFeature.Dependencies>(appComponent)
                    ?.getMainEntry() ?: return
            add(R.id.roster_fragment, featureFragment, TAG_TOP_FRAGMENT)
            if (displayFragment != null) {
                hide(displayFragment).commit()
                supportFragmentManager.beginTransaction().remove(displayFragment).commit()
            } else {
                commit()
            }
        }
    }

    private fun loadPlayground() {
        val transaction = supportFragmentManager.beginTransaction()
        val TAG_MIDDLE_FRAGMENT = "middle"
        val displayFragment = supportFragmentManager.findFragmentByTag(TAG_MIDDLE_FRAGMENT)
        val featureFragment =
            featureManager.getFeature<IotTestFeature, IotTestFeature.Dependencies>(appComponent)
                ?.getMainEntry() ?: return
        transaction.add(R.id.playground_fragment, featureFragment, TAG_MIDDLE_FRAGMENT)
        if (displayFragment != null) {
            transaction.hide(displayFragment).commit()
            supportFragmentManager.beginTransaction().remove(displayFragment).commit()
        } else {
            transaction.commit()
        }
    }

    private fun loadDynamicHistogram() {
        val transaction = supportFragmentManager.beginTransaction()
        val TAG_BOTTOM_FRAGMENT = "bottom"
        val displayFragment = supportFragmentManager.findFragmentByTag(TAG_BOTTOM_FRAGMENT)
        val featureFragment =
            featureManager.getFeature<IotHistogramFeature, IotHistogramFeature.Dependencies>(appComponent)
                ?.getMainEntry() ?: return
        transaction.add(R.id.histogram_fragment, featureFragment, TAG_BOTTOM_FRAGMENT)
        if (displayFragment != null) {
            transaction.hide(displayFragment).commit()
            supportFragmentManager.beginTransaction().remove(displayFragment).commit()
        } else {
            transaction.commit()
        }
    }
}
