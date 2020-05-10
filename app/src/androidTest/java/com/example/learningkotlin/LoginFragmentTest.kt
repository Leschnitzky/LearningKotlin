package com.example.learningkotlin

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.learningkotlin.ui.fragments.LoginFragment
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

    val scenario = null
    @Before
    fun setUP(){
        val scenario = launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_AppCompat)
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
    fun shouldDisplayEmptyEmailTextErrorIfNothingFilledIn() {
        onView(withId(R.id.login_button))
            .perform(click())
        onView(withText("Email must not be empty")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayEmptyPassTextErrorIfNothingFilledIn() {
        onView(withId(R.id.login_button))
            .perform(click())

        onView(withText("Password must not be empty")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayIllegalEmailTextIfUserContainsSpace() {
        val faultyUserText = "test#gmail.com"
        onView(withId(R.id.login_mail_edit_text))
            .perform(typeText(faultyUserText))
            .perform(closeSoftKeyboard())
        onView(withId(R.id.login_button))
            .perform(click())

        onView(withText("Illegal email format")).check(matches(isDisplayed()))
    }



}