package com.labappengineering.pomodoro.data.source

import androidx.lifecycle.LiveData
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.data.source.local.SessionsDao
import com.labappengineering.pomodoro.util.BaseDao
import com.labappengineering.pomodoro.util.DataSource
import com.labappengineering.pomodoro.util.IRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class SessionsRepository(
    sessionsDao: BaseDao<Session>
) : IRepository<Session> {
    private var sessionsDao = sessionsDao as SessionsDao

    override fun getEntities(): LiveData<List<Session>> {
        return sessionsDao.getSessions()
    }

    override fun getEntity(entityId: String): LiveData<Session?> {
        return sessionsDao.getSessionById(entityId)
    }

    override suspend fun saveEntity(entity: Session) {
        return sessionsDao.insert(entity)
    }

    override fun updateEntity(entity: Session) = runBlocking {
        val result = async { sessionsDao.update(entity) }
        result.await()
    }

    override suspend fun deleteAllEntitiess() {
        sessionsDao.deleteSessions()
    }

    override suspend fun deleteEntity(entityId: String) {
        sessionsDao.deleteSessionById(entityId)
    }
}