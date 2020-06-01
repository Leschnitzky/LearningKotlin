package com.example.learningkotlin.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.learningkotlin.viewmodels.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun bindViewModelFactory(factory: ViewModelProviderFactory) : ViewModelProvider.Factory {
        return factory
    }
}