package com.labappengineering.pomodoro.main.timer

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import android.graphics.drawable.ShapeDrawable

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import android.graphics.drawable.ColorDrawable




class TimerStateContext(
    private val widgets: List<View>,
    private val session: MutableLiveData<Session>
) {
    var currentState: TimerState? = null
    var stateCountDownTimer: StateCountDownTimer = StateCountDownTimer()
    var breakFinished = true
    var breakStartedStateType = BreakStartedStateType.SHORT
    init {
        decideStartingState()
    }


    fun doAction(){
        currentState?.doAction()
    }

    enum class TimerStateEnum {
        REPETITIONS_SESSIONS,
        REPETITIONS,
        SESSIONS,
        NOT_REPETITIONS_NOT_SESSIONS
    }

    fun checkTimerState() : TimerStateEnum {
        val currentSession = session.value!!
        if (currentSession.currentRepetition == currentSession.repetitions
            && currentSession.currentSessionPerDay+1 == currentSession.perDay){
            return TimerStateEnum.REPETITIONS_SESSIONS
        } else if(currentSession.currentRepetition == currentSession.repetitions
            && currentSession.currentSessionPerDay+1 != currentSession.perDay) {
            return TimerStateEnum.REPETITIONS
        } else if(currentSession.currentRepetition != currentSession.repetitions) {
            return TimerStateEnum.NOT_REPETITIONS_NOT_SESSIONS

        }
        return TimerStateEnum.NOT_REPETITIONS_NOT_SESSIONS
    }

    fun decideStartingState(){
        if(breakFinished){
            breakFinished = false
            currentState = SessionStartedState(
                this,
                widgets,
                session)
            resetProgresBarUI(currentState as SessionStartedState)
        } else{
            currentState = BreakStartedState(
                this,
                widgets,
                session,
                breakStartedStateType)
            resetProgresBarUI(currentState as BreakStartedState)
        }
    }

    fun resetProgresBarUI(state: ATimerState){
        val progressBar = state.findProgressBar(widgets)
        val textView = state.findTextView(widgets)
        val fab = state.findFab(widgets)
        fab!!.setImageResource(R.drawable.ic_timer)

        if(state is SessionStartedState){
            progressBar!!.progressDrawable.setColorFilter(Color.parseColor(session.value!!.sessionColor), PorterDuff.Mode.SRC_ATOP)
        } else if (state is BreakStartedState && state.breakStartedStateType == BreakStartedStateType.LONG){
            progressBar!!.progressDrawable.setColorFilter(Color.parseColor(session.value!!.longBreakColor), PorterDuff.Mode.SRC_ATOP)
        }else if (state is BreakStartedState && state.breakStartedStateType == BreakStartedStateType.SHORT){
            progressBar!!.progressDrawable.setColorFilter(Color.parseColor(session.value!!.shortBreakColor), PorterDuff.Mode.SRC_ATOP)
        }
        state.resetProgressBarUI(progressBar!!, textView!!, stateCountDownTimer)
    }

    private fun changeDrawableColor(background: Any, color: String){
        when (background) {
            is ShapeDrawable -> {
                // cast to 'ShapeDrawable'
                val shapeDrawable = background as ShapeDrawable
                shapeDrawable.paint.color = Color.parseColor(color)
            }
            is GradientDrawable -> {
                // cast to 'GradientDrawable'
                val gradientDrawable = background as GradientDrawable
                gradientDrawable.setColor(Color.parseColor(color))
            }
            is ColorDrawable -> {
                // alpha value may need to be set again after this call
                val colorDrawable = background as ColorDrawable
                colorDrawable.color = Color.parseColor(color)
            }
        }
    }

}