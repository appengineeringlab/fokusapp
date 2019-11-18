package com.labappengineering.pomodoro.settings

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
class SettingsActivityModule {
    @Provides
    fun settingsViewModel(repository: IRepository<Session>) : BaseViewModel<Session> {
        return SettingsViewModel(repository)
    }


    @Provides
    fun provideSessionsRepository(sessionsDao: BaseDao<Session>) :  IRepository<Session> {
        return SessionsRepository(sessionsDao)
    }

    @Provides
    fun provideSessionsDao(applicationContext: Context): BaseDao<Session> {
        return PomodoroDB.getInstance(applicationContext).sessionDao()
    }
}