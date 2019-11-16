package com.labappengineering.pomodoro.util

import androidx.lifecycle.LiveData

interface DataSource<T> {
    fun getEntities() : LiveData<List<T>>

    fun getEntity(entityId: String) : LiveData<T?>

    suspend fun saveEntity(entity: T)

    suspend fun updateEntity(entity: T)

    suspend fun deleteAllEntitiess()

    suspend fun deleteEntity(entityId: String)
}