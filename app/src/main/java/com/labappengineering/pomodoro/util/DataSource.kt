package com.labappengineering.pomodoro.util

import androidx.lifecycle.LiveData

interface DataSource<T> {

    fun getEntities() : LiveData<List<T>>

    fun getEntity(entityId: String): LiveData<T>

    fun saveEntity(entity: T)

    fun updateEntity(entity: T)

    fun deleteAllEntities()

    fun deleteEntity(entityId: String)
}