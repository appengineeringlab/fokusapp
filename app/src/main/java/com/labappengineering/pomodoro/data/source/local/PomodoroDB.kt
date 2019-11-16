package com.labappengineering.pomodoro.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.labappengineering.pomodoro.data.Session

@Database(entities = arrayOf(Session::class), version = 2)
abstract class PomodoroDB : RoomDatabase() {
    abstract fun sessionDao(): SessionsDao

    companion object{
        @Volatile // writes to this filed is immediately visible to other threads
        private var INSTANCE: PomodoroDB? = null

        fun getInstance(context: Context): PomodoroDB{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                if(INSTANCE == null) {
                    val instance = Room
                        .databaseBuilder(context.applicationContext,
                        PomodoroDB::class.java, "Pomodoro.db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
            return INSTANCE!!
        }
    }
}