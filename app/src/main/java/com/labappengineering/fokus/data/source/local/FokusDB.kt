package com.labappengineering.fokus.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.labappengineering.fokus.data.Session

@Database(entities = arrayOf(Session::class), version = 3)
abstract class FokusDB : RoomDatabase() {
    abstract fun sessionDao(): SessionsDao

    companion object{
        @Volatile // writes to this filed is immediately visible to other threads
        private var INSTANCE: FokusDB? = null

        fun getInstance(context: Context): FokusDB{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                if(INSTANCE == null) {
                    val instance = Room
                        .databaseBuilder(context.applicationContext,
                        FokusDB::class.java, "Pomodoro.db")
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