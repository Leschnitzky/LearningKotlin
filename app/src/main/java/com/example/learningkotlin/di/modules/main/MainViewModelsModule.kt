package com.example.learningkotlin.di.modules.main

import androidx.lifecycle.ViewModel
import com.example.learningkotlin.di.ViewModelKey
import com.example.learningkotlin.viewmodels.GalleryViewModel
import com.example.learningkotlin.viewmodels.UserLoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    abstract fun bindUserLoginViewModel(galleryViewModel: GalleryViewModel) : ViewModel

}