package com.example.learningkotlin.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learningkotlin.data.model.ErrorEvent
import com.example.learningkotlin.data.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException


class FirebaseAuthRepository private constructor(val firebaseAuth: FirebaseAuth) {

    companion object {
        const val TAG :String = "FirebaseAuthERROR";
        const val TAG2 :String = "FirebaseAuthDEBUG";
        var instance : FirebaseAuthRepository? = null
        fun getInstance(firebaseAuth: FirebaseAuth) : FirebaseAuthRepository{
            if(instance == null){
                return FirebaseAuthRepository(firebaseAuth)
            }
            return instance!!
        }
    }


    fun signInWithGoogleCredentials(authCredential: AuthCredential) : LiveData<User>?{
        val user = MutableLiveData<User>()
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val fbuser = firebaseAuth.currentUser
                    val fullName = fbuser?.displayName
                    val splittedName = fullName?.split(" ")
                    val firstName = if(splittedName.isNullOrEmpty()) splittedName!![0] else ""
                    val lastName = if(splittedName.isNullOrEmpty()) splittedName[1] else ""
                    user.value = User(
                        fbuser?.displayName,
                        "",
                        firstName,
                        lastName,
                        true,
                        "",
                        "",
                        ""
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
        firebaseAuth.signInWithEmailAndPassword(username,pass)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val fbuser = firebaseAuth.currentUser
                    val firstName = fbuser?.displayName ?: ""
                    val lastName = ""
                    user.value = User(
                        fbuser?.email,
                        pass,
                        firstName,
                        lastName,
                        false,
                        "",
                        "",
                        ""
                    )
                } else {
                    Log.e(TAG,task.exception?.message)
                    when((task.exception as FirebaseAuthException?)!!.errorCode){
                        "ERROR_USER_NOT_FOUND" -> {signInError.value = ErrorEvent.USERNAME_DOES_NOT_EXIST}
                        "ERROR_WRONG_PASSWORD" -> {signInError.value = ErrorEvent.WRONG_PASSWORD}
                    }
                }
            }
        return user
    }

    fun createUser(
        user: User,
        signUpError: MutableLiveData<ErrorEvent>
    ) : LiveData<User> {
        val userData = MutableLiveData<User>()
        firebaseAuth.createUserWithEmailAndPassword(user.email!!,user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.uid = firebaseAuth.currentUser!!.uid
                userData.value = user
                Log.v(TAG2,"ADDED $user")
            }
            else {
                Log.e(TAG,task.exception?.message)
                when((task.exception as FirebaseAuthException?)!!.errorCode){
                        "ERROR_EMAIL_ALREADY_IN_USE" -> {signUpError.value = ErrorEvent.USERNAME_ALREADY_EXISTS}
                  }
            }
        }
        return userData
    }

    fun getCurrentUser(): User {
        val fbuser = firebaseAuth.currentUser
        return User(
            fbuser?.email,
            "",
            "",
            "",
            false,
            "",
            "",
            ""
        )

    }


}