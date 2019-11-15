package com.labappengineering.pomodoro.alarm.data.source.local

import com.labappengineering.pomodoro.Alarm.data.source.AlarmsDataSource
import com.labappengineering.pomodoro.alarm.data.Alarm
import com.labappengineering.pomodoro.util.AppExecutors

class AlarmsLocalDataSource private constructor(
    val appExecutors: AppExecutors,
    val alarmsDao: AlarmsDao
) : AlarmsDataSource {
    override fun getAlarms(callback: AlarmsDataSource.LoadAlarmsCallback) {
        appExecutors.diskIO.execute{
            val alarms = alarmsDao.getAlarms()
            appExecutors.mainThread.execute {
                if(alarms.isEmpty()) {
                    callback.onDataNotAvailable()
                } else{
                    callback.onAlarmsLoaded(alarms)
                }
            }
        }
    }

    override fun getAlarm(alarmId: String, callback: AlarmsDataSource.GetAlarmCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveAlarm(alarm: Alarm) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completeAlarm(alarm: Alarm) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completeAlarm(alarmId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateAlarm(alarm: Alarm) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateAlarm(alarmId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearCompletedAlarms() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshAlarms() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllAlarms() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAlarm(alarmId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}