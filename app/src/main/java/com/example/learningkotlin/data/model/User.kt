package com.example.learningkotlin.data.model

data class User(
    val email: String?,
    val password: String,
    val firstName: String,
    val lastName: String,
    val isGoogleUser: Boolean = false,
    var uid: String = "",
    val summoner: String,
    val region: String
) {
}