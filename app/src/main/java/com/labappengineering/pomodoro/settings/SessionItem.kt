package com.labappengineering.pomodoro.settings
//settingsValues["sessionLength"] = sess.length.toString()
//settingsValues["shortBreakLength"] = sess.shortBreak.toString()
//settingsValues["longBreakLength"] = sess.longBreak.toString()
//settingsValues["sessionColor"] = sess.sessionColor.toString()
//settingsValues["shortBreakColor"] = sess.shortBreakColor.toString()
//settingsValues["longBreakColor"] = sess.longBreakColor.toString()
data class SessionItem(
    var sessionLength: Int,
    var shortBreakLength: Int,
    var longBreakLength: Int,
    var sessionColor: String,
    var shortBreakColor: String,
    var longBreakColor: String
)