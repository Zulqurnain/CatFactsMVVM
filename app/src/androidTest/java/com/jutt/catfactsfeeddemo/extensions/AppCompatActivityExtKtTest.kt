package com.jutt.catfactsfeeddemo.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import com.google.common.truth.Truth.*
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.view.activities.SplashActivity
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@SmallTest
class AppCompatActivityExtKtTest {

    @get:Rule
    val splashActivityRule = activityScenarioRule<SplashActivity>()

    @org.junit.Before
    fun setUp() {
    }

    @org.junit.After
    fun tearDown() {
        splashActivityRule.scenario.close()
    }

    @Test
    fun ToolbarSetup_correctly_true(){
        val myActivity = splashActivityRule.scenario
        myActivity.onActivity {
            val toolbar = it.findViewById<Toolbar>(R.id.toolbar)
            it.setupActionBar(toolbar){
                assertThat(this).isNotNull()
            }
        }
    }
}