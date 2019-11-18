package com.labappengineering.pomodoro.settings

import androidx.lifecycle.MutableLiveData
import com.labappengineering.pomodoro.data.source.SessionsRepository
import com.labappengineering.pomodoro.main.SessionsViewModel

class SettingsViewModel(sessionsRepository: SessionsRepository) : SessionsViewModel(sessionsRepository) {
    var sessionItemLiveData: MutableLiveData<List<SessionItem>> = MutableLiveData()
}