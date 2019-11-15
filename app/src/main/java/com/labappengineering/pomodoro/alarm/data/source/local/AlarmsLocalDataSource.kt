package com.labappengineering.pomodoro.alarm.data.source.local

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

    override fun updateEntityById(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateEntity(entity: Alarm) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshEntities() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllEntities() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteEntity(entityId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}