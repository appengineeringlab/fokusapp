package com.labappengineering.pomodoro.main

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
class MainActivityModule {
    @Provides
    fun sessionsViewModel(repository: IRepository<Session>) : BaseViewModel<Session> {
        return SessionsViewModel(repository)
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