package com.example.learningkotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthRepository {
    val auth :FirebaseAuth = FirebaseAuth.getInstance()


    fun signInWithGoogleCredentials(authCredential: AuthCredential) : LiveData<User>?{
        val user = MutableLiveData<User>()
        auth.signInWithCredential(authCredential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val fbuser = auth.currentUser
                    user.value = User(fbuser?.displayName,"",true)
                } else {
                    user.value = null
                }
            }
        return user
    }

    fun signInWithGoogleWithUserAndPass(username: String,pass: String) : LiveData<User>?{
        val user = MutableLiveData<User>()
        auth.signInWithEmailAndPassword(username,pass)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val fbuser = auth.currentUser
                    user.value = User(fbuser?.displayName,pass,false)
                } else {
                    user.value = null
                }
            }
        return user
    }





}