package com.labappengineering.pomodoro.main.timer

import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.util.Converters

class SessionStartedState(
     timerStateContext: TimerStateContext,
     widgets: List<View>,
     session: MutableLiveData<Session>
) : StartedState(timerStateContext, widgets, session){

}