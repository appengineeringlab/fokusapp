package com.labappengineering.pomodoro.splash
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.util.BaseViewModel
import com.labappengineering.pomodoro.util.IRepository

class SplashViewModel(repository: IRepository<Session>) : BaseViewModel<Session>(repository) {
    fun initializeDB(sessions : List<Session>){
        if(sessions == null ){
            insert(Session(length = 1, repetitions = 2, perDay = 3))
        } else if(sessions != null && sessions.isEmpty()) {
             insert(Session(length = 1, repetitions = 2, perDay = 3))
        }

    }

    fun clearDB() {
            deleteAll()
    }

}