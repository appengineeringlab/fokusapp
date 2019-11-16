package com.labappengineering.pomodoro.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.data.source.SessionsRepository
import kotlinx.coroutines.launch

open class SessionsViewModel(val sessionsRepository: SessionsRepository) : ViewModel(){
    fun getAllSessions() : LiveData<List<Session>> {
        return sessionsRepository.getEntities()
    }

    fun getSessionById(entityId: String) : LiveData<Session?>{
        return sessionsRepository.getEntity(entityId)
    }

    fun insert(session: Session) = viewModelScope.launch {
        sessionsRepository.saveEntity(session)
    }

    fun update(session: Session) = viewModelScope.launch {
        sessionsRepository.updateEntity(session)
    }

    fun deleteAll() = viewModelScope.launch {
        sessionsRepository.deleteAllEntitiess()
    }

    fun deleteById(entityId: String) = viewModelScope.launch {
        sessionsRepository.deleteEntity(entityId)
    }
}