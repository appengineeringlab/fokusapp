package com.labappengineering.pomodoro.data.source

import androidx.lifecycle.LiveData
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.data.source.local.SessionsDao
import com.labappengineering.pomodoro.util.DataSource

class SessionsRepository(
    val sessionsDao: SessionsDao
) : DataSource<Session> {
    override fun getEntities(): LiveData<List<Session>> {
        return sessionsDao.getSessions()
    }

    override fun getEntity(entityId: String): LiveData<Session?> {
        return sessionsDao.getSessionById(entityId)
    }

    override suspend fun saveEntity(entity: Session) {
        return sessionsDao.insertSession(entity)
    }

    override suspend fun updateEntity(entity: Session) {
        sessionsDao.updateSession(entity)
    }

    override suspend fun deleteAllEntitiess() {
        sessionsDao.deleteSessions()
    }

    override suspend fun deleteEntity(entityId: String) {
        sessionsDao.deleteSessionById(entityId)
    }
}