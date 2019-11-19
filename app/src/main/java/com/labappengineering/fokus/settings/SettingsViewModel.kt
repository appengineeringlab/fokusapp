package com.labappengineering.fokus.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.labappengineering.fokus.data.Session
import com.labappengineering.fokus.util.BaseViewModel
import com.labappengineering.fokus.util.IRepository

class SettingsViewModel(repository: IRepository<Session>) : BaseViewModel<Session>(repository) {
    var sessionItemLiveData: MutableLiveData<ArrayList<SessionItem>> = MutableLiveData()
    var sessionsLiveData: LiveData<List<Session>> = MutableLiveData()
    var sessionLiveData: MutableLiveData<Session> = MutableLiveData()
    var session: Session? = null
}