package com.labappengineering.pomodoro.main.timer

import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
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
        if(stateCountDownTimer.countDownTimer == null) {
            setTimerValues(session, stateCountDownTimer)
            for (view in widgets){
                if (view is ProgressBar){
                    progressBar = view
                    continue
                }
                if(view is TextView){
                    textView = view
                }
            }
            setProgressBarValues(progressBar!!, stateCountDownTimer)
            startCountDownTimer(progressBar!!, stateCountDownTimer, textView!!)
        } else {
            timerStateContext.currentState = StoppedState(
                timerStateContext,
                widgets,
                session)
            (timerStateContext.currentState as StoppedState).doAction()
        }
    }

    private fun setProgressBarValues(progressBar: ProgressBar, stateCountDownTimer: StateCountDownTimer){
        progressBar.max = (stateCountDownTimer.timeCountInMilliSeconds / 1000L).toInt()
        progressBar.progress = (stateCountDownTimer.timeCountInMilliSeconds / 1000L).toInt()
    }

    private fun setTimerValues(session: Session, stateCountDownTimer: StateCountDownTimer){
        var time = session.length
        // assigning values after converting to milliseconds
        stateCountDownTimer.timeCountInMilliSeconds = (time * 60 * 1000).toLong()
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