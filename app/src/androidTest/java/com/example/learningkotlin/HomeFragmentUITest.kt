package com.example.learningkotlin

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.learningkotlin.ui.home.LoginFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import utils.DataBindingIdlingResourceRule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginFragmentTests {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityTestRule)
    val scenario = null
    @Before
    fun setUP(){
        val scenario = launchFragmentInContainer<LoginFragment>()
    }

    @Test
    fun shouldDisplayLoginButton(){
        onView(withId(R.id.login_button)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplaySignUpButton(){
        onView(withId(R.id.sign_up_button)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayBothInputTextLayouts(){
        onView(withId(R.id.textInputLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.textInputLayout2)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayEmptyUserTextErrorIfNothingFilledIn() {
        onView(withId(R.id.login_button)).perform(click())

        onView(withText("Username must not be empty")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayEmptyPassTextErrorIfNothingFilledIn() {
        onView(withId(R.id.login_button)).perform(click())

        onView(withText("Password must not be empty")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayIllegalUserTextIfUserContainsSpace() {
        val faultyUserText = "Test Test"
        onView(withId(R.id.login_user_edit_text)).perform(typeText(faultyUserText))
        onView(withId(R.id.login_button)).perform(click())

        onView(withText("Illegal user name")).check(matches(isDisplayed()))
    }

}