package com.labappengineering.pomodoro.Alarm.data.source

import com.labappengineering.pomodoro.alarm.data.Alarm

interface AlarmsDataSource {
    interface LoadAlarmsCallback {

        fun onAlarmsLoaded(alarms: List<Alarm>)

        fun onDataNotAvailable()
    }

    interface GetAlarmCallback {

        fun onAlarmLoaded(alarm: Alarm)

        fun onDataNotAvailable()
    }

    fun getAlarms(callback: LoadAlarmsCallback)

    fun getAlarm(alarmId: String, callback: GetAlarmCallback)

    fun saveAlarm(alarm: Alarm)

    fun completeAlarm(alarm: Alarm)

    fun completeAlarm(alarmId: String)

    fun activateAlarm(alarm: Alarm)

    fun activateAlarm(alarmId: String)

    fun clearCompletedAlarms()

    fun refreshAlarms()

    fun deleteAllAlarms()

    fun deleteAlarm(alarmId: String)
}