package com.labappengineering.pomodoro.main.timer

import android.view.View
import androidx.lifecycle.MutableLiveData

import com.labappengineering.pomodoro.data.Session

class SessionStartedState(
     timerStateContext: TimerStateContext,
     widgets: List<View>,
     session: MutableLiveData<Session>
) : StartedState(timerStateContext, widgets, session){

}