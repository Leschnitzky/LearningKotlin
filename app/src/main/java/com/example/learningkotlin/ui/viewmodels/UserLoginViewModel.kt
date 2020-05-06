package com.example.learningkotlin.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learningkotlin.data.repositories.FirebaseAuthRepository
import com.example.learningkotlin.data.model.ErrorEvent
import com.example.learningkotlin.data.repositories.FirestoreRepository
import com.example.learningkotlin.data.model.User
import com.google.firebase.auth.GoogleAuthCredential


class UserLoginViewModel : ViewModel() {

    val signInError: MutableLiveData<ErrorEvent> = MutableLiveData()
    val USER_PATTERN = "^[_A-z0-9]*((-|)*[_A-z0-9])*\$".toRegex()
    val firebaseAuthRepository =
        FirebaseAuthRepository()
    val firestoreRepository =
        FirestoreRepository()
    var authenticatedUserLiveData: LiveData<User>? = null


    fun isValidUser(userString: String): Boolean {
        return USER_PATTERN.containsMatchIn(userString)
    }


    fun signInWithGoogleCredentials(googleAuthCredential : GoogleAuthCredential) {
        authenticatedUserLiveData = firebaseAuthRepository.signInWithGoogleCredentials(googleAuthCredential);
    }

    fun signInWithUserAndPass(user : String, pass: String) {
        authenticatedUserLiveData = firebaseAuthRepository.signInWithGoogleWithUserAndPass(user,pass,signInError);
    }

    //Todo: check how to look for in Firebase
    fun isEmailInDB(emailString: String?): Boolean {
        return false
    }

    fun signUpWithUser(user: User) {
        firebaseAuthRepository.createUser(user)
    }

}