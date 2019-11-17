package com.labappengineering.pomodoro.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.data.source.SessionsRepository
import com.labappengineering.pomodoro.main.timer.TimerStateContext
import kotlinx.coroutines.launch

open class SessionsViewModel(val sessionsRepository: SessionsRepository) : ViewModel(){

    var sessionsLiveData: LiveData<List<Session>> = MutableLiveData()
    var sessionLiveData: MutableLiveData<Session> = MutableLiveData()
    var sess: Session? = null

    var timerStateContext: TimerStateContext? = null

    fun getAllSessions() : LiveData<List<Session>> {
        return sessionsRepository.getEntities()
    }

    fun getSessionById(entityId: String) : LiveData<Session?>{
        return sessionsRepository.getEntity(entityId)
    }

    fun insert(session: Session) = viewModelScope.launch {
        sessionsRepository.saveEntity(session)
    }

    fun update(session: Session) : Int {
        return sessionsRepository.updateEntity(session)
    }

    fun deleteAll() = viewModelScope.launch {
        sessionsRepository.deleteAllEntitiess()
    }

    fun deleteById(entityId: String) = viewModelScope.launch {
        sessionsRepository.deleteEntity(entityId)
    }
}