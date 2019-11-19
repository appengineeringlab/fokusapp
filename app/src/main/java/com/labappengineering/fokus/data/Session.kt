package com.labappengineering.fokus.data

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
    var currentSessionPerDay: Int = 1,
    var sessionColor: String = "#1977F3",
    var shortBreakColor: String = "#80bf00",
    var longBreakColor: String = "#fc6b03"
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Session

        if (id != other.id) return false
        if (length != other.length) return false
        if (shortBreak != other.shortBreak) return false
        if (longBreak != other.longBreak) return false
        if (repetitions != other.repetitions) return false
        if (perDay != other.perDay) return false
        if (currentRepetition != other.currentRepetition) return false
        if (currentSessionPerDay != other.currentSessionPerDay) return false
        if (sessionColor != other.sessionColor) return false
        if (shortBreakColor != other.shortBreakColor) return false
        if (longBreakColor != other.longBreakColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + length
        result = 31 * result + shortBreak
        result = 31 * result + longBreak
        result = 31 * result + repetitions
        result = 31 * result + perDay
        result = 31 * result + currentRepetition
        result = 31 * result + currentSessionPerDay
        result = 31 * result + sessionColor.hashCode()
        result = 31 * result + shortBreakColor.hashCode()
        result = 31 * result + longBreakColor.hashCode()
        return result
    }
}