package com.labappengineering.pomodoro.main.timer

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.labappengineering.pomodoro.data.Session

class TimerStateContext(
    widgets: List<View>,
    session: Session
) {
    var currentState: TimerState? = null
    var stateCountDownTimer: StateCountDownTimer = StateCountDownTimer()

    init {
        currentState = StartedState(
            this,
            widgets,
            session)
    }


    fun doAction(){
        currentState?.doAction()
    }
}