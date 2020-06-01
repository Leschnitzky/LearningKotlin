package com.example.learningkotlin.di

import android.app.Application
import com.example.learningkotlin.BaseApplication
import com.example.learningkotlin.di.modules.ActivityBuilderModule
import com.example.learningkotlin.di.modules.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        ViewModelFactoryModule::class
    )
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application) : Builder
        fun build() : AppComponent
    }
}