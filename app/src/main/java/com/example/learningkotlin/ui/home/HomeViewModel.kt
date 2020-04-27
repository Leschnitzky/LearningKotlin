package com.example.learningkotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.learningkotlin.data.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential


class HomeViewModel : ViewModel() {
    val USER_PATTERN = "^[_A-z0-9]*((-|)*[_A-z0-9])*\$".toRegex()
    val firebaseAuthRepository = FirebaseAuthRepository()
    var authenticatedUserLiveData: LiveData<FirebaseUser>? = null


    fun isValidUser(userString: String): Boolean {
        return USER_PATTERN.containsMatchIn(userString)
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuthRepository.getCurrentUser()
    }

    fun signInWithGoogleCredentials(googleAuthCredential : GoogleAuthCredential) {
        authenticatedUserLiveData = firebaseAuthRepository.signInWithGoogleCredentials(googleAuthCredential);
    }

    fun signInWithUserAndPass(user : String, pass: String) {
        authenticatedUserLiveData = firebaseAuthRepository.signInWithGoogleWithUserAndPass(user,pass);
    }
}