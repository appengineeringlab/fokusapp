package com.labappengineering.pomodoro.di


import com.labappengineering.pomodoro.splash.SplashActivity
import com.labappengineering.pomodoro.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun splashActivity(): SplashActivity
}