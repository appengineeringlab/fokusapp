package com.labappengineering.pomodoro.pomodoro.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.labappengineering.pomodoro.alarm.data.Alarm
import java.util.*
import kotlin.collections.ArrayList

// JvmOverloads creates constructor all constructors
// up to N-1 parameters if constructor receives N parameters
@Entity
data class Pomodoro @JvmOverloads constructor (
    @PrimaryKey @ColumnInfo(name = "_id") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "length") var length: Int = 25,
    @ColumnInfo(name = "shortBreak") var shortBreak: Int = 5,
    @ColumnInfo(name = "longBreak") var longBreak: Int = 25,
    @ColumnInfo(name = "goal") var goal: Int = 10,
    @ColumnInfo(name = "repetitionsPerSession") var repetitionsPerSession: Int = 4,
    @ColumnInfo(name = "alarms") var alarms: List<Alarm> = ArrayList()
) {
}