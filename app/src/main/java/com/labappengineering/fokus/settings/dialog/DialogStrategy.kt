package com.labappengineering.fokus.settings.dialog

import androidx.lifecycle.MutableLiveData

interface DialogStrategy<T> {
    fun show(items: MutableLiveData<ArrayList<T>>)
}