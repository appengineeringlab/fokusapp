package com.labappengineering.fokus.settings

import android.content.Context
import com.labappengineering.fokus.data.Session
import com.labappengineering.fokus.data.source.SessionsRepository
import com.labappengineering.fokus.data.source.local.FokusDB
import com.labappengineering.fokus.util.BaseViewModel
import com.labappengineering.fokus.util.BaseDao
import com.labappengineering.fokus.util.IRepository
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
        return FokusDB.getInstance(applicationContext).sessionDao()
    }
}