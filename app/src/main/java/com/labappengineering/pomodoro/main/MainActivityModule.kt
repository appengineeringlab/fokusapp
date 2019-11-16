package com.labappengineering.pomodoro.main

import android.content.Context
import com.labappengineering.pomodoro.data.source.SessionsRepository
import com.labappengineering.pomodoro.data.source.local.PomodoroDB
import com.labappengineering.pomodoro.data.source.local.SessionsDao
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun sessionsViewModel(repository: SessionsRepository) : SessionsViewModel {
        return SessionsViewModel(repository)
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