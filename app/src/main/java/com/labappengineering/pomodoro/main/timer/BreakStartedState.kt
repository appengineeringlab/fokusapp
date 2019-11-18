package com.labappengineering.pomodoro.main.timer

import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.util.Converters

enum class BreakStartedStateType {
    SHORT,
    LONG
}

class BreakStartedState(
    timerStateContext: TimerStateContext,
    widgets: List<View>,
    session: MutableLiveData<Session>,
    val breakStartedStateType: BreakStartedStateType
) : StartedState(timerStateContext, widgets, session) {

    override fun doAction() {
        super.doAction()
        timerStateContext.breakFinished = true
    }

    override fun startCountDownTimer(
        progressBar: ProgressBar,
        stateCountDownTimer: StateCountDownTimer,
        textView: TextView
    ) {
        stateCountDownTimer.countDownTimer = object : CountDownTimer(stateCountDownTimer.timeCountInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView.text = Converters.hmsTimeFormatter(millisUntilFinished)
                progressBar.progress = (millisUntilFinished / 1000).toInt()

            }
            override fun onFinish() {
                val currentSession = session.value!!
                session.value = currentSession.copy()
                timerStateContext.currentState = StoppedState(
                    timerStateContext,
                    widgets,
                    session)
                (timerStateContext.currentState as StoppedState).doAction()
                timerStateContext.breakFinished = true
            }

        }.start()
        stateCountDownTimer.countDownTimer!!.start()
    }

    override fun setTimerValues(session: Session, stateCountDownTimer: StateCountDownTimer) {
        var time : Int = if(breakStartedStateType == BreakStartedStateType.LONG){
            session.longBreak
        } else {
            session.shortBreak
        }
        // assigning values after converting to milliseconds
        stateCountDownTimer.timeCountInMilliSeconds = Converters.minutesToMilliseconds(time)
    }
}