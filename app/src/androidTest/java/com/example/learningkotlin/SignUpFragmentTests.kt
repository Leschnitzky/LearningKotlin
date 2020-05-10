package com.example.learningkotlin

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.learningkotlin.ui.fragments.LoginFragment
import com.example.learningkotlin.ui.fragments.SignUpFragment
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
class SignUpFragmentTests {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityTestRule)
    val scenario = null
    @Before
    fun setUP(){
        val scenario = launchFragmentInContainer<SignUpFragment>(themeResId = R.style.Theme_AppCompat)
    }

    @Test
    fun shouldDisplayInputLayoutFirstName(){
        onView(withId(R.id.sign_up_first_name)).check(matches(isDisplayed()))
    }
    @Test
    fun shouldDisplayInputLayoutLastName(){
        onView(withId(R.id.sign_up_last_name)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayInputLayoutEmail(){
        onView(withId(R.id.sign_up_email)).check(matches(isDisplayed()))
    }
    
    @Test
    fun shouldDisplayInputPassword(){
        onView(withId(R.id.sign_up_password)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayConfirmPassword(){
        onView(withId(R.id.sign_up_confirm_password)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplaySubmitButton(){
        onView(withId(R.id.sign_up_btn_confirmation)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayEmptyFieldTextErrorIfNothingFilledIn() {
        onView(withId(R.id.login_button)).perform(click())
        onView(withText("The following field is empty")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayIllegalEmailTextIfUserContainsSpace() {
        val faultyUserText = "test#gmail.com"
        onView(withId(R.id.sign_up_email)).perform(typeText(faultyUserText))
        onView(withId(R.id.sign_up_btn_confirmation)).perform(click())

        onView(withText("Illegal email format")).check(matches(isDisplayed()))
    }



}