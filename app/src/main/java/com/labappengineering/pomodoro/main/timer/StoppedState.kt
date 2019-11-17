package com.labappengineering.pomodoro.main.timer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session

class StoppedState(
    timerStateContext: TimerStateContext,
    widgets: List<View>,
    session: MutableLiveData<Session>
) : ATimerState(timerStateContext, widgets, session) {
    override fun doAction() {
        val stateCountDownTimer =timerStateContext.stateCountDownTimer
        if(stateCountDownTimer.countDownTimer != null) {
            stateCountDownTimer.countDownTimer!!.cancel()
            stateCountDownTimer.countDownTimer = null

            val progressBar = findProgressBar(widgets)
            val textView = findTextView(widgets)
            val fab = findFab(widgets)
            fab!!.setImageResource(R.drawable.ic_timer)
            resetProgressBarUI(progressBar!!, textView!!, stateCountDownTimer)

            timerStateContext.currentState = StartedState(
                timerStateContext,
                widgets,
                session)
        }
    }

}