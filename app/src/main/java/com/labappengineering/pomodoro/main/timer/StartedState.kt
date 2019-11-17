package com.labappengineering.pomodoro.main.timer

import android.app.IntentService
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.util.Converters

abstract class StartedState(
    timerStateContext: TimerStateContext,
    widgets: List<View>,
    session: MutableLiveData<Session>
) : ATimerState(timerStateContext, widgets, session){
    override fun doAction() {
        val stateCountDownTimer = timerStateContext.stateCountDownTimer
        var progressBar: ProgressBar?
        var textView: TextView?
        var fab: FloatingActionButton?

        if(stateCountDownTimer.countDownTimer == null) {

            progressBar = findProgressBar(widgets)
            textView = findTextView(widgets)
            fab = findFab(widgets)
            fab!!.setImageResource(R.drawable.ic_timer_off)
            resetProgressBarUI(progressBar!!, textView!!, stateCountDownTimer)
            startCountDownTimer(progressBar, stateCountDownTimer, textView)
        } else {
            timerStateContext.currentState = StoppedState(
                timerStateContext,
                widgets,
                session)
            (timerStateContext.currentState as StoppedState).doAction()
        }
    }

    protected open fun startCountDownTimer(progressBar: ProgressBar, stateCountDownTimer: StateCountDownTimer, textView: TextView){


        stateCountDownTimer.countDownTimer = object : CountDownTimer(stateCountDownTimer.timeCountInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView.text = Converters.hmsTimeFormatter(millisUntilFinished)
                progressBar.progress = (millisUntilFinished / 1000).toInt()

            }
            override fun onFinish() {
                val currentSession = session.value!!
                val timerState = timerStateContext.checkTimerState()
                if (timerState == TimerStateContext.TimerStateEnum.REPETITIONS_SESSIONS){
                    currentSession.perDay += 1
                    currentSession.currentSessionPerDay += 1
                    currentSession.currentRepetition = 1
                    timerStateContext.breakStartedStateType = BreakStartedStateType.LONG
                } else if(timerState == TimerStateContext.TimerStateEnum.REPETITIONS) {
                    currentSession.currentSessionPerDay += 1
                    currentSession.currentRepetition = 1
                    timerStateContext.breakStartedStateType = BreakStartedStateType.LONG
                } else if(timerState == TimerStateContext.TimerStateEnum.NOT_REPETITIONS_NOT_SESSIONS) {
                    currentSession.currentRepetition += 1
                    timerStateContext.breakStartedStateType = BreakStartedStateType.SHORT
                }

                session.value = currentSession.copy()
                timerStateContext.currentState = StoppedState(
                    timerStateContext,
                    widgets,
                    session)
                (timerStateContext.currentState as StoppedState).doAction()
            }

        }.start()
        stateCountDownTimer.countDownTimer!!.start()

    }

}