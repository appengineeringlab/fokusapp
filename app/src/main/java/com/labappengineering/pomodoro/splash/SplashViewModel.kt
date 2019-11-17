package com.labappengineering.pomodoro.splash


import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.data.source.SessionsRepository
import com.labappengineering.pomodoro.main.SessionsViewModel
import kotlinx.coroutines.launch



class SplashViewModel(sessionsRepository: SessionsRepository) : SessionsViewModel(sessionsRepository) {
    fun initializeDB(sessions : List<Session>){
        if(sessions == null ){
            viewModelScope.launch{
                insert(Session(length = 1, repetitions = 2, perDay = 3)).join()
            }
        } else if(sessions != null && sessions.isEmpty()) {
            viewModelScope.launch{
                insert(Session(length = 1, repetitions = 2, perDay = 3)).join()
            }
        }

    }

    fun clearDB() {
        viewModelScope.launch{
            deleteAll().join()
        }
    }

}