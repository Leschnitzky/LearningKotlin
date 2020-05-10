package com.example.learningkotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.core.internal.deps.guava.base.Joiner.on
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.learningkotlin.data.repositories.FirebaseAuthRepository
import com.example.learningkotlin.data.repositories.FirestoreRepository
import com.example.learningkotlin.ui.viewmodels.UserLoginViewModel
import com.weatherapp.util.UserLoginViewModelFactory
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFrameworkLogicTest {


    @Test
    fun checkingValidEmail(){
        val userLoginViewModel = UserLoginViewModel(FirebaseAuthRepository(),FirestoreRepository())
        val normalEmail = "test@gmail.com"
        val missingDot = "test@gmail"
        val missingAt = "testgmail.com"
        val missingBoth = "testgmailcom"
        val hasSpace = "te st@gmai l.c om"


        assertTrue(userLoginViewModel.isValidEmail(normalEmail))
        assertFalse(userLoginViewModel.isValidEmail(missingDot))
        assertFalse(userLoginViewModel.isValidEmail(missingAt))
        assertFalse(userLoginViewModel.isValidEmail(missingBoth))
        assertFalse(userLoginViewModel.isValidEmail(hasSpace))
    }

    @Test
    fun shouldReturnFalseIfEmailIsNotInDB(){
        val mock = mockk<FirestoreRepository>()
        every { mock.checkEmailInDB("test@gmail.com")} returns MutableLiveData(false)
        every { mock.checkEmailInDB("test2@gmail.com")} returns MutableLiveData(true)
        val userLoginViewModel = UserLoginViewModel(FirebaseAuthRepository(),mock)

        assertFalse(userLoginViewModel.isEmailInDB("test@gmail.com").value!!)
        assertTrue(userLoginViewModel.isEmailInDB("test2@gmail.com").value!!)
    }

}
