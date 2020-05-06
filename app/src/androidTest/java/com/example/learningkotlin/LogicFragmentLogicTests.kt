package com.example.learningkotlin

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.learningkotlin.ui.fragments.LoginFragment
import com.example.learningkotlin.ui.viewmodels.UserLoginViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import utils.DataBindingIdlingResourceRule

@RunWith(AndroidJUnit4::class)
class LoginFrameworkLogicTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityTestRule)
    val scenario = null

    @Before
    fun setUP() {
        val scenario = launchFragmentInContainer<LoginFragment>()
    }



    @Test
    fun displaySnackbarWhenUserIsLoggedIn(){
        /* Given */
        val mockedViewModel = mock<UserLoginViewModel>() {
            given { it.getCurrentUser() }.willReturn { null }
        }




    }

}
