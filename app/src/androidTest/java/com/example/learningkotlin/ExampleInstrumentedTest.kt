package com.example.learningkotlin

import android.content.Intent
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction_Factory.newInstance
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.base.IdlingResourceRegistry_Factory.newInstance
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.learningkotlin.ui.home.HomeFragment
import com.example.learningkotlin.ui.home.HomeViewModel
import com.google.common.util.concurrent.Runnables.doNothing
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import utils.DataBindingIdlingResourceRule
import java.lang.reflect.Array.newInstance


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RobolectricActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityTestRule)

//DataBindingIdlingResourceRule
//    @Before
//    fun setUp() {
//        activityTestRule.launchActivity(Intent())
//
//        activityTestRule.activity.supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragment_home_layout, HomeFragment())
//            .commit()
//    }

    @Test
    fun errorShouldDisplayOnEmptyEditText() {
        val scenario = launchFragmentInContainer<HomeFragment>()
        onView(withId(R.id.login_button))
            .check(matches(isDisplayed()))
    }
}