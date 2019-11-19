package com.labappengineering.fokus.main.timer

import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.labappengineering.fokus.R
import com.labappengineering.fokus.data.Session
import com.labappengineering.fokus.util.Converters
// TODO: onfinish() is different, use callbacks, template method, higher-order functions...
// TODO: So manu methods for solving this, do not override the whole method
abstract class StartedState(
    timerStateContext: TimerStateContext,
    widgets: List<View>,
    session: MutableLiveData<Session>
) : BaseTimerState(timerStateContext, widgets, session){
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

        timerStateContext.breakFinished = true
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
                timerStateContext.breakFinished = false
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