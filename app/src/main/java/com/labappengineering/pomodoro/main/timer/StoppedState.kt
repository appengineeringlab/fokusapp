package com.labappengineering.pomodoro.main.timer

import android.view.View
import com.labappengineering.pomodoro.data.Session

class StoppedState(
    timerStateContext: TimerStateContext,
    widgets: List<View>,
    session: Session
) : ATimerState(timerStateContext, widgets, session) {
    override fun doAction() {
        val stateCountDownTimer =timerStateContext.stateCountDownTimer
        if(stateCountDownTimer.countDownTimer != null) {
            stateCountDownTimer.countDownTimer!!.cancel()
            stateCountDownTimer.countDownTimer = null
            timerStateContext.currentState = StartedState(
                timerStateContext,
                widgets,
                session)
        }
    }

}