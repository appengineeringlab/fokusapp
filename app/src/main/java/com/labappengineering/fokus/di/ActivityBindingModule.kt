package com.labappengineering.fokus.di


import com.labappengineering.fokus.main.MainActivity
import com.labappengineering.fokus.main.MainActivityModule
import com.labappengineering.fokus.settings.SettingsActivity
import com.labappengineering.fokus.settings.SettingsActivityModule
import com.labappengineering.fokus.splash.SplashActivity
import com.labappengineering.fokus.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [SettingsActivityModule::class])
    internal abstract fun settingsActivity(): SettingsActivity
}