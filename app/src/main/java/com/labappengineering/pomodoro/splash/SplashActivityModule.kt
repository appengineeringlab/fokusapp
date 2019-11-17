package com.labappengineering.pomodoro.splash

import android.content.Context
import com.labappengineering.pomodoro.data.source.SessionsRepository
import com.labappengineering.pomodoro.data.source.local.PomodoroDB
import com.labappengineering.pomodoro.data.source.local.SessionsDao
import dagger.Module
import dagger.Provides


@Module
class SplashActivityModule {
    @Provides
    fun splashViewModel(repository: SessionsRepository) : SplashViewModel {
        return SplashViewModel(repository)
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