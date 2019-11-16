package com.labappengineering.pomodoro.main.timer

import android.view.View
import com.labappengineering.pomodoro.data.Session

abstract class ATimerState(
    protected val timerStateContext: TimerStateContext,
    protected val widgets: List<View>,
    protected val session: Session) : TimerState{

}