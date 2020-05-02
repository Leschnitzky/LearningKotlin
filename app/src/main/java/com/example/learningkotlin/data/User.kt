package com.example.learningkotlin.data

data class User(val email: String?, val password: String, val firstName: String, val lastName: String, val isGoogleUser: Boolean = false) {
}