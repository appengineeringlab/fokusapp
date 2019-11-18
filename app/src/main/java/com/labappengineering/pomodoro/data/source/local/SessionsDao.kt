package com.labappengineering.pomodoro.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.util.BaseDao

@Dao
interface SessionsDao : BaseDao<Session> {
    @Query("SELECT * FROM Sessions")
    fun getSessions(): LiveData<List<Session>>
    
    @Query("SELECT * FROM Sessions WHERE _id = :sessionId")
    fun getSessionById(sessionId: String): LiveData<Session?>

    @Query("DELETE FROM Sessions WHERE _id = :sessionId")
    suspend fun deleteSessionById(sessionId: String): Int

    @Query("DELETE FROM Sessions")
    suspend fun deleteSessions()

}