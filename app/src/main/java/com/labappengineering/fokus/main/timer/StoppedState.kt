package com.labappengineering.fokus.main.timer

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.labappengineering.fokus.data.Session

class StoppedState(
    timerStateContext: TimerStateContext,
    widgets: List<View>,
    session: MutableLiveData<Session>
) : BaseTimerState(timerStateContext, widgets, session) {
    override fun doAction() {
        val stateCountDownTimer =timerStateContext.stateCountDownTimer
        if(stateCountDownTimer.countDownTimer != null) {
            stateCountDownTimer.countDownTimer!!.cancel()
            stateCountDownTimer.countDownTimer = null
            timerStateContext.decideStartingState()

        }
    }

}