package com.weatherapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learningkotlin.data.repositories.FirebaseAuthRepository
import com.example.learningkotlin.data.repositories.FirestoreRepository
import com.example.learningkotlin.viewmodels.UserLoginViewModel


class UserLoginViewModelFactory constructor(
    private val firestoreRepository: FirestoreRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
):
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserLoginViewModel::class.java) ->
                UserLoginViewModel(firebaseAuthRepository,firestoreRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

