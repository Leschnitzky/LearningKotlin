package com.example.learningkotlin.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.learningkotlin.data.FirebaseAuthRepository
import com.example.learningkotlin.data.User
import com.google.firebase.auth.GoogleAuthCredential


class UserLoginViewModel : ViewModel() {

    val USER_PATTERN = "^[_A-z0-9]*((-|)*[_A-z0-9])*\$".toRegex()
    val firebaseAuthRepository = FirebaseAuthRepository()
    var authenticatedUserLiveData: LiveData<User>? = null


    fun isValidUser(userString: String): Boolean {
        return USER_PATTERN.containsMatchIn(userString)
    }


    fun signInWithGoogleCredentials(googleAuthCredential : GoogleAuthCredential) {
        authenticatedUserLiveData = firebaseAuthRepository.signInWithGoogleCredentials(googleAuthCredential);
    }

    fun signInWithUserAndPass(user : String, pass: String) {
        authenticatedUserLiveData = firebaseAuthRepository.signInWithGoogleWithUserAndPass(user,pass);
    }
}