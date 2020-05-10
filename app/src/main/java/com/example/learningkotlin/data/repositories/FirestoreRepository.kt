package com.example.learningkotlin.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learningkotlin.data.model.User

open class FirestoreRepository {
    companion object {
        const val USER_TABLE_NAME = "users"
    }

    fun addUser(user: User) {

    }

    fun checkEmailInDB(s: String): LiveData<Boolean> {
        val isInDB = MutableLiveData<Boolean>()

        // Do the operation

        return isInDB
    }
}

