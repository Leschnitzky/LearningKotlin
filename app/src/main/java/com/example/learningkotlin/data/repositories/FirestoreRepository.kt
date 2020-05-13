package com.example.learningkotlin.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learningkotlin.data.model.ErrorEvent
import com.example.learningkotlin.data.model.User
import com.example.learningkotlin.data.model.firestore.UserFirestoreDoc
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

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

    fun addUser(
        user: User
    ): LiveData<User> {
        val liveDataToRet = MutableLiveData<User>()
        val userData = UserFirestoreDoc(
            user.email!!,
            user.firstName,
            user.lastName,
            user.uid
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

    fun getUserData(user: User): LiveData<User>? {
        val userData = MutableLiveData<User>()
        database.collection(USER_TABLE_NAME)
            .whereEqualTo("email",user.email)
            .get()
            .addOnSuccessListener {
                documents ->
                userData.value = documents.documents.first().toObject<UserFirestoreDoc>()?.toUser()
            }
        return userData
    }
}

