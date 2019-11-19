package com.labappengineering.fokus.splash
import com.labappengineering.fokus.data.Session
import com.labappengineering.fokus.util.BaseViewModel
import com.labappengineering.fokus.util.IRepository

class SplashViewModel(repository: IRepository<Session>) : BaseViewModel<Session>(repository) {
    fun initializeDB(sessions : List<Session>){
        if(sessions.isEmpty() ){
            insert(Session(length = 1, repetitions = 2, perDay = 3))
        }
    }

    fun clearDB() {
            deleteAll()
    }

}