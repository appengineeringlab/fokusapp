package com.labappengineering.pomodoro.alarm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Alarm @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "_id") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "volume") var volume: Boolean = true,
    @ColumnInfo(name = "volumeLevel") var volumeLevel: Int = 100,
    @ColumnInfo(name = "sound") var sound: String = ""
) {
}