package com.example.learningkotlin.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.learningkotlin.data.repositories.FirebaseAuthRepository
import com.example.learningkotlin.data.repositories.FirestoreRepository


class UserLoginViewModelFactory(application: Application,
                                firebaseAuthRepository: FirebaseAuthRepository? = null
                                , firestoreRepository: FirestoreRepository? = null ) {

}