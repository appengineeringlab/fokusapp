package com.labappengineering.fokus.di

import android.content.Context
import com.labappengineering.fokus.FokusApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class
    ])
interface ApplicationComponent : AndroidInjector<FokusApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}