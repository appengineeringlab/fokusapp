package com.labappengineering.pomodoro.alarm.data.source.local

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.labappengineering.pomodoro.Alarm.data.source.AlarmsDataSource
import com.labappengineering.pomodoro.alarm.data.Alarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class AlarmsLocalDataSource private constructor(
    val alarmsDao: AlarmsDao
) : AlarmsDataSource {

    private fun<T> returnData(data: T) : T {
        return data
    }
    override fun getEntities(): LiveData<List<Alarm>> {
        var alarmsLiveData: LiveData<List<Alarm>> = MutableLiveData<List<Alarm>>()
        runBlocking {
            val getDataJob  = async(Dispatchers.IO) {
                val alarms = alarmsDao.getAlarms()
                returnData(alarms)
            }

            val updateDataJob = async(Dispatchers.Main) {
                alarmsLiveData = getDataJob.await()
            }
            runBlocking {
                updateDataJob.await()
            }
        }


        return alarmsLiveData
    }

    override fun getEntity(entityId: String): LiveData<Alarm> {
        var alarmLiveData: LiveData<Alarm> = MutableLiveData<Alarm>()
        runBlocking {
            val getDataJob  = async(Dispatchers.IO) {
                val alarm = alarmsDao.getAlarmById(entityId)
                returnData(alarm)
            }

            val updateDataJob = async(Dispatchers.Main) {
                alarmLiveData = getDataJob.await()!!
            }
            runBlocking {
                updateDataJob.await()
            }
        }
        return alarmLiveData
    }



    override fun saveEntity(entity: Alarm) {
        runBlocking {
            val saveJob = async(Dispatchers.IO) {
                alarmsDao.insertAlarm(entity)
            }

            runBlocking {
                saveJob.await()
            }
        }
    }

    override fun updateEntity(entity: Alarm) {
        runBlocking {
            val saveJob = async(Dispatchers.IO) {
                alarmsDao.updateAlarm(entity)
            }

            runBlocking {
                saveJob.await()
            }
        }
    }

    override fun deleteAllEntities() {
        runBlocking {
            val saveJob = async(Dispatchers.IO) {
                alarmsDao.deleteAlarms()
            }

            runBlocking {
                saveJob.await()
            }
        }
    }

    override fun deleteEntity(entityId: String) {
        runBlocking {
            val saveJob = async(Dispatchers.IO) {
                alarmsDao.deleteAlarmByID(entityId)
            }

            runBlocking {
                saveJob.await()
            }
        }
    }

    companion object {
        private var INSTANCE: AlarmsLocalDataSource? = null

        @JvmStatic
        fun getInstance(tasksDao: AlarmsDao): AlarmsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(AlarmsLocalDataSource::javaClass) {
                    INSTANCE = AlarmsLocalDataSource(tasksDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

}