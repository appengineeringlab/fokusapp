package com.labappengineering.pomodoro.settings.dialog

import androidx.lifecycle.MutableLiveData

interface DialogStrategy<T> {
    fun show(items: MutableLiveData<ArrayList<T>>)
}