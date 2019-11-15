package com.labappengineering.pomodoro.alarm.data.source.local

import androidx.room.*
import com.labappengineering.pomodoro.alarm.data.Alarm

@Dao interface AlarmsDao {
    @Query("SELECT * FROM Alarms")
    fun getAlarms(): List<Alarm>

    @Query("SELECT * FROM Alarms WHERE _id = :id")
    fun getAlarmById(id: String): Alarm?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(alarm: Alarm)
    /*
        @return the number of alarms updated. This should always be 1.
    */
    @Update
    fun updateAlarm(alarm: Alarm): Int


    @Query("DELETE FROM Alarms where _id = :id ")
    fun deleteAlarmByID(id: String)

    @Query("DELETE FROM alarms")
    fun deleteAlarms()

}