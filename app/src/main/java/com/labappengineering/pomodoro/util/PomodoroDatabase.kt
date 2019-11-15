package com.labappengineering.pomodoro.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.labappengineering.pomodoro.alarm.data.Alarm
import com.labappengineering.pomodoro.alarm.data.source.local.AlarmsDao

@Database(entities =  arrayOf(Alarm::class), version = 1)
abstract class PomodoroDatabase : RoomDatabase(){
    abstract  fun alarmsDao(): AlarmsDao

    companion object {
        private var INSTANCE: PomodoroDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context) : PomodoroDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PomodoroDatabase::class.java, "Pomoddoro.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}