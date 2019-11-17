package com.labappengineering.pomodoro.settings

import android.content.Context
import com.labappengineering.pomodoro.data.source.SessionsRepository
import com.labappengineering.pomodoro.data.source.local.PomodoroDB
import com.labappengineering.pomodoro.data.source.local.SessionsDao
import com.labappengineering.pomodoro.main.SessionsViewModel
import com.labappengineering.pomodoro.splash.SplashViewModel
import dagger.Module
import dagger.Provides

@Module
class SettingsActivityModule {
    @Provides
    fun settingsViewModel(repository: SessionsRepository) : SettingsViewModel {
        return SettingsViewModel(repository)
    }


    @Provides
    fun provideSessionsRepository(sessionsDao: SessionsDao) : SessionsRepository {
        return SessionsRepository(sessionsDao)
    }

    @Provides
    fun provideSessionsDao(applicationContext: Context): SessionsDao {
        return PomodoroDB.getInstance(applicationContext).sessionDao()
    }
}