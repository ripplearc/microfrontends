package com.ripplearc.heavy.groundvisual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ripplearc.heavy.common_features.FeatureManager
import com.ripplearc.heavy.common_features.IotRosterFeature
import com.ripplearc.heavy.common_features.getFeature
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var featureManager: FeatureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(this)

        loadRoster()
    }

    private fun loadRoster() {
        val transaction = supportFragmentManager.beginTransaction()
        val TAG_TOP_FRAGMENT = "top"
        val displayFragment = supportFragmentManager.findFragmentByTag(TAG_TOP_FRAGMENT)
        val featureFragment =
            featureManager.getFeature<IotRosterFeature, IotRosterFeature.Dependencies>(appComponent)
                ?.getMainEntry() ?: return
        transaction.add(R.id.fragment_container, featureFragment, TAG_TOP_FRAGMENT)
        if (displayFragment != null) {
            transaction.hide(displayFragment).commit()
            supportFragmentManager.beginTransaction().remove(displayFragment).commit()
        } else {
            transaction.commit()
        }

    }
}
