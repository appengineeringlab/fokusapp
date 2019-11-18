package com.labappengineering.pomodoro.splash

import android.content.Context
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.data.source.SessionsRepository
import com.labappengineering.pomodoro.data.source.local.PomodoroDB
import com.labappengineering.pomodoro.util.BaseViewModel
import com.labappengineering.pomodoro.util.BaseDao
import com.labappengineering.pomodoro.util.IRepository
import dagger.Module
import dagger.Provides


@Module
class SplashActivityModule {
    @Provides
    fun splashViewModel(repository: IRepository<Session>) : BaseViewModel<Session> {
        return SplashViewModel(repository)
    }


    @Provides
    fun provideSessionsRepository(sessionsDao: BaseDao<Session>) : IRepository<Session> {
        return SessionsRepository(sessionsDao)
    }

    @Provides
    fun provideSessionsDao(applicationContext: Context): BaseDao<Session> {
        return PomodoroDB.getInstance(applicationContext).sessionDao()
    }
}