package com.example.learningkotlin.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learningkotlin.data.model.ErrorEvent
import com.example.learningkotlin.data.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException


class FirebaseAuthRepository {
    val auth :FirebaseAuth = FirebaseAuth.getInstance()
    companion object {
        public const val TAG :String = "FirebaseAuth";
    }


    fun signInWithGoogleCredentials(authCredential: AuthCredential) : LiveData<User>?{
        val user = MutableLiveData<User>()
        auth.signInWithCredential(authCredential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val fbuser = auth.currentUser
                    val fullName = fbuser?.displayName
                    val splittedName = fullName?.split(" ")
                    val firstName = if(splittedName.isNullOrEmpty()) splittedName!![0] else ""
                    val lastName = if(splittedName.isNullOrEmpty()) splittedName[1] else ""
                    user.value = User(
                        fbuser?.displayName,
                        "",
                        firstName,
                        lastName,
                        true
                    )
                } else {
                    user.value = null
                }
            }
        return user
    }

    fun signInWithGoogleWithUserAndPass(
        username: String,
        pass: String,
        signInError: MutableLiveData<ErrorEvent>
    ) : LiveData<User>?{
        val user = MutableLiveData<User>()
        auth.signInWithEmailAndPassword(username,pass)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val fbuser = auth.currentUser
                    val firstName = fbuser?.displayName ?: ""
                    val lastName = ""
                    user.value = User(
                        fbuser?.email,
                        pass,
                        firstName,
                        lastName,
                        false
                    )
                } else {
                    logErrorMessage(task.exception?.message)
                    when((task.exception as FirebaseAuthException?)!!.errorCode){
                        "ERROR_USER_NOT_FOUND" -> {signInError.value = ErrorEvent.USERNAME_DOES_NOT_EXIST}
                        "ERROR_WRONG_PASSWORD" -> {signInError.value = ErrorEvent.WRONG_PASSWORD}
                    }
                }
            }
        return user
    }

    fun createUser(user: User) : LiveData<User> {
        val userData = MutableLiveData<User>()
        auth.createUserWithEmailAndPassword(user.email!!,user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val fbuser = auth.currentUser
                val fullName = fbuser?.displayName
                val splittedName = fullName?.split(" ")
                val firstName = if(splittedName.isNullOrEmpty()) splittedName!![0] else ""
                val lastName = if(splittedName.isNullOrEmpty()) splittedName[1] else ""

                userData.value = User(
                    fbuser?.displayName,
                    user.password,
                    firstName,
                    lastName,
                    false
                )
            }
            else {
                logErrorMessage(task.exception?.message);
            }
        }
        return userData
    }

    private fun logErrorMessage(message: String?) {
        if(message == null) {
            return
        } else {
            Log.d(TAG,message)
        }

    }

}