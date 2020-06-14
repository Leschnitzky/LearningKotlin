package com.example.learningkotlin.data.model.firestore

import com.example.learningkotlin.data.model.User

data class UserFirestoreDoc(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val uid: String = "",
    val summoner: String = "",
    val region: String = ""
){
    fun toUser(): User{
        return User(
            email = email,
            password = "",
            firstName = firstName,
            lastName = lastName,
            uid = uid,
            summoner = summoner,
            region = region
        )
    }
}