package com.labappengineering.pomodoro.main.timer

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session

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
        state.resetProgressBarUI(progressBar!!, textView!!, stateCountDownTimer)
    }

}