package com.labappengineering.pomodoro.main.timer

import android.widget.ProgressBar
import android.widget.TextView
import com.labappengineering.pomodoro.data.Session

interface TimerState {
    fun doAction()
}