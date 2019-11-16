package com.labappengineering.pomodoro.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sessions")
data class Session @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name="_id")
    var id: String = UUID.randomUUID().toString(),

    var length: Int = 25,
    var shortBreak: Int = 5,
    var longBreak: Int = 25,
    var repetitions: Int = 4,
    var perDay: Int = 10,
    var currentRepetition: Int = 1,
    var currentSessionPerDay: Int = 1
){
}