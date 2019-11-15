package com.labappengineering.pomodoro.alarm.data.source.local

import androidx.room.Room
import androidx.test.InstrumentationRegistry.getContext
import androidx.test.runner.AndroidJUnit4

import com.labappengineering.pomodoro.alarm.data.Alarm
import com.labappengineering.pomodoro.util.PomodoroDatabase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class AlaramsDaoTest {
    private lateinit var database: PomodoroDatabase

    @Before
    fun initDb(){
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            getContext(),
            PomodoroDatabase::class.java).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertAlarmAndGetById(){
        database.alarmsDao().insertAlarm(DEFAULT_ALARM)

        val loaded = database.alarmsDao().getAlarmById(DEFAULT_ALARM.id)

        assertAlarm(
            loaded,
            DEFAULT_ID,
            DEFAULT_VOLUME,
            DEFAULT_VOLUME_LEVEL,
            DEFAULT_SOUND
        )
    }


    private fun assertAlarm(
        alarm: Alarm?,
        id: String,
        volume: Boolean,
        volumeLevel: Int,
        sound: String
    ) {
        MatcherAssert.assertThat<Alarm>(alarm as Alarm, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(alarm.id, CoreMatchers.`is`(id))
        MatcherAssert.assertThat(alarm.volume, CoreMatchers.`is`(volume))
        MatcherAssert.assertThat(alarm.volumeLevel, CoreMatchers.`is`(volumeLevel))
        MatcherAssert.assertThat(alarm.sound, CoreMatchers.`is`(sound))
    }

/*
 @ColumnInfo(name = "volume") var volume: Boolean = true,
    @ColumnInfo(name = "volumeLevel") var volumeLevel: Int = 100,
    @ColumnInfo(name = "sound") var sound: String = ""
 */
    companion object {

        private val DEFAULT_VOLUME = true
        private val DEFAULT_VOLUME_LEVEL = 80
        private val DEFAULT_ID = "id"
        private val DEFAULT_SOUND = "default_sound.mp3"
        private val DEFAULT_ALARM = Alarm(DEFAULT_ID, DEFAULT_VOLUME, DEFAULT_VOLUME_LEVEL, DEFAULT_SOUND)

        private val NEW_VOLUME = false
        private val NEW_VOLUME_LEVEL = 50
        private val NEW_SOUND = "new_sound.mp3"
    }
}