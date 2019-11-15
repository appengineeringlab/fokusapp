package com.labappengineering.pomodoro.alarm.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.labappengineering.pomodoro.alarm.data.Alarm

@Dao interface AlarmsDao {
    @Query("SELECT * FROM Alarms")
    fun getAlarms(): LiveData<List<Alarm>>

    @Query("SELECT * FROM Alarms WHERE _id = :id")
    fun getAlarmById(id: String): LiveData<Alarm>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm)
    /*
        @return the number of alarms updated. This should always be 1.
    */
    @Update
    suspend fun updateAlarm(alarm: Alarm): Int


    @Query("DELETE FROM Alarms where _id = :id ")
    suspend fun deleteAlarmByID(id: String)

    @Query("DELETE FROM alarms")
    suspend fun deleteAlarms()

}