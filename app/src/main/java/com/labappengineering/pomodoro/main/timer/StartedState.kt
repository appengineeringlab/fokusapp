package com.labappengineering.pomodoro.main.timer

import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.util.Converters

class StartedState(
     timerStateContext: TimerStateContext,
     widgets: List<View>,
     session: Session
) : ATimerState(timerStateContext, widgets, session){

    override fun doAction() {
        val stateCountDownTimer = timerStateContext.stateCountDownTimer
        var progressBar: ProgressBar? = null
        var textView: TextView? = null
        var fab: FloatingActionButton? = null

        if(stateCountDownTimer.countDownTimer == null) {

            progressBar = findProgressBar(widgets)
            textView = findTextView(widgets)
            fab = findFab(widgets)
            fab!!.setImageResource(R.drawable.ic_timer_off)
            resetProgressBarUI(progressBar!!, textView!!, stateCountDownTimer)
            startCountDownTimer(progressBar!!, stateCountDownTimer, textView!!)
        } else {
            timerStateContext.currentState = StoppedState(
                timerStateContext,
                widgets,
                session)
            (timerStateContext.currentState as StoppedState).doAction()
        }
    }

    private fun startCountDownTimer(progressBar: ProgressBar, stateCountDownTimer: StateCountDownTimer, textView: TextView){
        stateCountDownTimer.countDownTimer = object : CountDownTimer(stateCountDownTimer.timeCountInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                textView.text = Converters.hmsTimeFormatter(millisUntilFinished)

                progressBar.progress = (millisUntilFinished / 1000).toInt()


            }

            override fun onFinish() {

                textView.setText(Converters.hmsTimeFormatter(stateCountDownTimer.timeCountInMilliSeconds))
                // call to initialize the progress bar values
                setProgressBarValues(progressBar, stateCountDownTimer)
            }

        }.start()
        stateCountDownTimer.countDownTimer!!.start()
    }

}