package com.labappengineering.pomodoro.settings
//settingsValues["sessionLength"] = sess.length.toString()
//settingsValues["shortBreakLength"] = sess.shortBreak.toString()
//settingsValues["longBreakLength"] = sess.longBreak.toString()
//settingsValues["sessionColor"] = sess.sessionColor.toString()
//settingsValues["shortBreakColor"] = sess.shortBreakColor.toString()
//settingsValues["longBreakColor"] = sess.longBreakColor.toString()
data class SessionItem(
    var key: String,
    var value: String,
    var name: String
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SessionItem

        if (key != other.key) return false
        if (value != other.value) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}