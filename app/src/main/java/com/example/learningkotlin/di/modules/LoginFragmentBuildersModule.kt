package com.example.learningkotlin.di.modules

import com.example.learningkotlin.MainActivity
import com.example.learningkotlin.ui.fragments.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment() : LoginFragment

}