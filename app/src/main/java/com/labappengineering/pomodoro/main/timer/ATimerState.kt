package com.labappengineering.pomodoro.main.timer

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.util.Converters

abstract class ATimerState(
    protected val timerStateContext: TimerStateContext,
    protected val widgets: List<View>,
    protected val session: Session) : TimerState{


    protected fun  findProgressBar(widgets: List<View>): ProgressBar?{
        for (view in widgets){
            if (view is ProgressBar){
                return view
            }
        }
        return null
    }

    protected fun  findTextView(widgets: List<View>): TextView?{
        for (view in widgets){
            if (view is TextView){
                return view
            }
        }
        return null
    }

    protected fun findFab(widgets: List<View>): FloatingActionButton? {
        for (view in widgets){
            if (view is FloatingActionButton){
                return view
            }
        }
        return null
    }

    protected fun resetProgressBarUI(progressBar: ProgressBar, textView: TextView, stateCountDownTimer: StateCountDownTimer){
        setTimerValues(session, stateCountDownTimer)
        setProgressBarValues(progressBar!!, stateCountDownTimer)
        textView.text = Converters.hmsTimeFormatter(stateCountDownTimer.timeCountInMilliSeconds)
    }

    protected fun setProgressBarValues(progressBar: ProgressBar, stateCountDownTimer: StateCountDownTimer){
        progressBar.max = (stateCountDownTimer.timeCountInMilliSeconds / 1000L).toInt()
        progressBar.progress = (stateCountDownTimer.timeCountInMilliSeconds / 1000L).toInt()
    }

    private fun setTimerValues(session: Session, stateCountDownTimer: StateCountDownTimer){
        var time = session.length
        // assigning values after converting to milliseconds
        stateCountDownTimer.timeCountInMilliSeconds = Converters.minutesToMilliseconds(time)
    }

}