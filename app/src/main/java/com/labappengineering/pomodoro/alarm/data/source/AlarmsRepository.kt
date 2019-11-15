package com.labappengineering.pomodoro.alarm.data.source

import com.labappengineering.pomodoro.Alarm.data.source.AlarmsDataSource
import com.labappengineering.pomodoro.alarm.data.Alarm
import com.labappengineering.pomodoro.alarm.data.source.local.AlarmsLocalDataSource
import com.labappengineering.pomodoro.util.DataSource
import kotlinx.coroutines.NonCancellable.isCompleted
import java.util.ArrayList
import java.util.LinkedHashMap

class AlarmsRepository (
    val alarmsLocalDataSource: AlarmsLocalDataSource
) : AlarmsDataSource {
    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    var cachedAlarms: LinkedHashMap<String, Alarm> = LinkedHashMap()

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    var cacheIsDirty = false

    override fun getEntities(callback: DataSource.LoadEntitiesCallback<Alarm>) {
            alarmsLocalDataSource.getEntities(object : DataSource.LoadEntitiesCallback<Alarm> {
                override fun onEntitiesLoaded(alarms: List<Alarm>) {
                    refreshCache(alarms)
                    callback.onEntitiesLoaded(ArrayList(cachedAlarms.values))
                }

                override fun onDataNotAvailable() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })

    }

    override fun getEntity(entityId: String, callback: DataSource.GetEntityCallback<Alarm>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveEntity(entity: Alarm) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateEntity(entity: Alarm) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllEntities() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteEntity(entityId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun refreshCache(tasks: List<Alarm>) {
        cachedAlarms.clear()
        tasks.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(alarms: List<Alarm>) {
        alarmsLocalDataSource.deleteAllEntities()
        for (alarm in alarms) {
            alarmsLocalDataSource.saveEntity(alarm)
        }
    }

    private inline fun cacheAndPerform(alarm: Alarm, perform: (Alarm) -> Unit) {
        val cachedAlarm = Alarm(alarm.id, alarm.volume, alarm.volumeLevel, alarm.sound)
        cachedAlarms[cachedAlarm.id] = cachedAlarm
        perform(cachedAlarm)
    }
}