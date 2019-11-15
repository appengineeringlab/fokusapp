package com.labappengineering.pomodoro.alarm.data.source.local

import androidx.annotation.VisibleForTesting
import com.labappengineering.pomodoro.Alarm.data.source.AlarmsDataSource
import com.labappengineering.pomodoro.alarm.data.Alarm
import com.labappengineering.pomodoro.util.AppExecutors
import com.labappengineering.pomodoro.util.DataSource

class AlarmsLocalDataSource private constructor(
    val appExecutors: AppExecutors,
    val alarmsDao: AlarmsDao
) : AlarmsDataSource {
    override fun getEntities(callback: DataSource.LoadEntitiesCallback<Alarm>) {
        appExecutors.diskIO.execute {
            val alarms = alarmsDao.getAlarms()
            appExecutors.mainThread.execute {
                if(alarms.isEmpty()){
                    callback.onDataNotAvailable()
                } else {
                    callback.onEntitiesLoaded(alarms)
                }
            }
        }
    }

    override fun getEntity(entityId: String, callback: DataSource.GetEntityCallback<Alarm>) {
        appExecutors.diskIO.execute {
            val alarm = alarmsDao.getAlarmById(entityId)
            appExecutors.mainThread.execute {
                if(alarm == null){
                    callback.onDataNotAvailable()
                } else {
                    callback.onEntityLoaded(alarm)
                }
            }
        }    }

    override fun saveEntity(entity: Alarm) {
        appExecutors.diskIO.execute {
            alarmsDao.insertAlarm(entity)
        }
    }

    override fun updateEntity(entity: Alarm) {
        appExecutors.diskIO.execute {
            alarmsDao.updateAlarm(entity)
        }
    }

    override fun deleteAllEntities() {
        appExecutors.diskIO.execute {
            alarmsDao.deleteAlarms()
        }
    }

    override fun deleteEntity(entityId: String) {
        appExecutors.diskIO.execute {
            alarmsDao.deleteAlarmByID(entityId)
        }
    }

    companion object {
        private var INSTANCE: AlarmsLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, tasksDao: AlarmsDao): AlarmsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(AlarmsLocalDataSource::javaClass) {
                    INSTANCE = AlarmsLocalDataSource(appExecutors, tasksDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

}