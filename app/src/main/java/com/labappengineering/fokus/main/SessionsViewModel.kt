package com.labappengineering.fokus.main
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.labappengineering.fokus.data.Session
import com.labappengineering.fokus.main.timer.TimerStateContext
import com.labappengineering.fokus.util.BaseViewModel
import com.labappengineering.fokus.util.IRepository

open class SessionsViewModel(repository: IRepository<Session>
) : BaseViewModel<Session>(repository) {

    var sessionsLiveData: LiveData<List<Session>> = MutableLiveData()
    var sessionLiveData: MutableLiveData<Session> = MutableLiveData()
    var session: Session? = null

    var timerStateContext: TimerStateContext? = null

}