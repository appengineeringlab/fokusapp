package com.labappengineering.pomodoro.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.data.source.SessionsRepository
import kotlinx.coroutines.launch

class SessionsViewModel(val sessionsRepository: SessionsRepository) : ViewModel(){
    fun getAllSessions() : LiveData<List<Session>> {
        return sessionsRepository.getEntities()
    }

    fun insert(session: Session) = viewModelScope.launch {
        sessionsRepository.saveEntity(session)
    }
}