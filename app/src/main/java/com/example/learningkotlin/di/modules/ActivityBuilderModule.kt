package com.example.learningkotlin.di.modules

import com.example.learningkotlin.MainActivity
import com.example.learningkotlin.di.modules.main.MainViewModelsModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = arrayOf(
            LoginFragmentBuildersModule::class,
            MainViewModelsModule::class))
    abstract fun contributeMainActivity() : MainActivity
}