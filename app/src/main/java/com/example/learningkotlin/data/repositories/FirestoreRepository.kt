package com.example.learningkotlin.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learningkotlin.data.model.User
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository private constructor(val database: FirebaseFirestore){
    companion object {
        const val TAG = "Firestore"
        const val TAG2 = "FirestoreERROR"
        const val USER_TABLE_NAME = "users"
        var instance : FirestoreRepository? = null

        fun getInstance(firestoreRepository: FirebaseFirestore): FirestoreRepository {
            if (instance == null)  // NOT thread safe!
                instance = FirestoreRepository(firestoreRepository)

            return instance!!
        }
    }

    fun addUser(user: User): LiveData<User> {
        val liveDataToRet = MutableLiveData<User>()
        val userData = hashMapOf(
            "uid" to user.uid,
            "firstName" to user.firstName,
            "lastName" to user.lastName,
            "email" to user.email
        )
        database.collection(USER_TABLE_NAME).add(userData).addOnSuccessListener {
            liveDataToRet.value = user
        }.addOnFailureListener {
            Log.e(TAG2, it.message)
        }
        return liveDataToRet
    }

    fun checkEmailInDB(s: String): LiveData<Boolean> {
        val isInDB = MutableLiveData<Boolean>()

        // Do the operation

        return isInDB
    }
}

