package com.labappengineering.pomodoro.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labappengineering.pomodoro.data.Session
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(repository: IRepository<T>) : ViewModel() {
    private var repository = repository

    fun getAllEntities() : LiveData<List<T>> {
        return repository.getEntities()
    }

    fun getEntityById(entityId: String) : LiveData<T?> {
        return repository.getEntity(entityId)
    }

    fun insert(entity: T) = viewModelScope.launch {
        repository.saveEntity(entity)
    }

    fun update(entity: T) : Int {
        return repository.updateEntity(entity)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllEntitiess()
    }

    fun deleteById(entityId: String) = viewModelScope.launch {
        repository.deleteEntity(entityId)
    }
}