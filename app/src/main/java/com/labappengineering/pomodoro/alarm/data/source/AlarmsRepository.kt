package com.labappengineering.pomodoro.alarm.data.source

import androidx.lifecycle.LiveData
import com.labappengineering.pomodoro.Alarm.data.source.AlarmsDataSource
import com.labappengineering.pomodoro.alarm.data.Alarm
import com.labappengineering.pomodoro.alarm.data.source.local.AlarmsLocalDataSource
import java.util.LinkedHashMap

class AlarmsRepository (
    val alarmsLocalDataSource: AlarmsLocalDataSource
) : AlarmsDataSource {

    override fun getEntities() : LiveData<List<Alarm>> {
        return alarmsLocalDataSource.getEntities()
    }

    override fun getEntity(entityId: String) : LiveData<Alarm> {
        return alarmsLocalDataSource.getEntity(entityId)
    }

    override fun saveEntity(entity: Alarm) {
        alarmsLocalDataSource.saveEntity(entity)
    }

    override fun updateEntity(entity: Alarm) {
        alarmsLocalDataSource.updateEntity(entity)
    }

    override fun deleteAllEntities() {
        alarmsLocalDataSource.deleteAllEntities()
    }

    override fun deleteEntity(entityId: String) {
        alarmsLocalDataSource.deleteEntity(entityId)
    }

    companion object {

        private var INSTANCE: AlarmsRepository? = null

        @JvmStatic fun getInstance(alarmsLocalDataSource: AlarmsLocalDataSource) =
            INSTANCE ?: synchronized(AlarmsDataSource::class.java) {
                INSTANCE ?: AlarmsRepository(alarmsLocalDataSource)
                    .also { INSTANCE = it }
            }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}