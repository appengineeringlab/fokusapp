package com.labappengineering.pomodoro.alarm.data.source.local

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.labappengineering.pomodoro.Alarm.data.source.AlarmsDataSource
import com.labappengineering.pomodoro.alarm.data.Alarm
import com.labappengineering.pomodoro.util.AppExecutors
import com.labappengineering.pomodoro.util.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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
            val getData  = async(Dispatchers.IO) {
                val alarms = alarmsDao.getAlarms()
                returnData(alarms)
            }

            val updateData = async(Dispatchers.Main) {
                alarmsLiveData = getData.await()
            }
            runBlocking {
                updateData.await()
            }
        }


        return alarmsLiveData
    }

    override fun getEntity(entityId: String): LiveData<Alarm> {
        var alarmLiveData: LiveData<Alarm> = MutableLiveData<Alarm>()
        appExecutors.diskIO.execute {
            val alarm = alarmsDao.getAlarmById(entityId)
            appExecutors.mainThread.execute {
                if (alarm != null){
                    alarmLiveData = alarm!!
                }
            }
        }
        return alarmLiveData
    }



    override fun saveEntity(entity: Alarm) {
        val deferred = async(Dispatchers.IO) {
            println("${Thread.currentThread()} has run.")
        }
        appExecutors.diskIO.execute {
            runBlocking {
                val job = launch(Dispatchers.IO) {
                    alarmsDao.insertAlarm(entity)
                }
            }
        }
    }

    override fun updateEntity(entity: Alarm) {
        appExecutors.diskIO.execute {
            runBlocking {
                alarmsDao.updateAlarm(entity)
            }
        }
    }

    override fun deleteAllEntities() {
        appExecutors.diskIO.execute {
            runBlocking {
                alarmsDao.deleteAlarms()
            }
        }
    }

    override fun deleteEntity(entityId: String) {
        appExecutors.diskIO.execute {
            runBlocking {
                alarmsDao.deleteAlarmByID(entityId)
            }
        }
    }

    companion object {
        private var INSTANCE: AlarmsLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, tasksDao: AlarmsDao): AlarmsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(AlarmsLocalDataSource::javaClass) {
                    INSTANCE = AlarmsLocalDataSource(appExecutors, tasksDao)
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