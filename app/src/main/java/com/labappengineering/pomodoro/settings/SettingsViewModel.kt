package com.labappengineering.pomodoro.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.labappengineering.pomodoro.data.source.SessionsRepository
import com.labappengineering.pomodoro.main.SessionsViewModel

class SettingsViewModel(sessionsRepository: SessionsRepository) : SessionsViewModel(sessionsRepository) {
    var sessionItem: SessionItem? = null
    var sessionItemLiveData: MutableLiveData<SessionItem> = MutableLiveData()
}