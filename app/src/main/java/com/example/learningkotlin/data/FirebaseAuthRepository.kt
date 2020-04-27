package com.example.learningkotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthRepository {
    val auth :FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signInWithGoogleCredentials(authCredential: AuthCredential) : LiveData<FirebaseUser>?{
        val user = MutableLiveData<FirebaseUser>()
        auth.signInWithCredential(authCredential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    user.value = auth.currentUser
                } else {
                    user.value = null
                }
            }
        return user
    }

    fun signInWithGoogleWithUserAndPass(username: String,pass: String) : LiveData<FirebaseUser>?{
        val user = MutableLiveData<FirebaseUser>()
        auth.signInWithEmailAndPassword(username,pass)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    user.value = auth.currentUser
                } else {
                    user.value = null
                }
            }
        return user
    }





}