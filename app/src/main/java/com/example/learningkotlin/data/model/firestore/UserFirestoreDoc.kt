package com.example.learningkotlin.data.model.firestore

import com.example.learningkotlin.data.model.User

data class UserFirestoreDoc(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val uid: String = ""
){
    fun toUser(): User{
        return User(
            email = email,
            firstName = firstName,
            password = "",
            lastName = lastName,
            uid = uid
        )
    }
}