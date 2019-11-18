package com.labappengineering.pomodoro.main
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.main.timer.TimerStateContext
import com.labappengineering.pomodoro.util.BaseViewModel
import com.labappengineering.pomodoro.util.IRepository

open class SessionsViewModel(repository: IRepository<Session>
) : BaseViewModel<Session>(repository) {

    var sessionsLiveData: LiveData<List<Session>> = MutableLiveData()
    var sessionLiveData: MutableLiveData<Session> = MutableLiveData()
    var session: Session? = null

    var timerStateContext: TimerStateContext? = null

}