package com.labappengineering.pomodoro.util

interface DataSource<T> {
    interface LoadEntitiesCallback<T> {

        fun onEntitiesLoaded(entities: List<T>)

        fun onDataNotAvailable()
    }

    interface GetEntityCallback<T> {

        fun onEntityLoaded(entity: T)

        fun onDataNotAvailable()
    }

    fun getEntities(callback: LoadEntitiesCallback<T>)

    fun getEntity(entityId: String, callback: GetEntityCallback<T>)

    fun saveEntity(entity: T)

    fun updateEntityById(taskId: String)

    fun updateEntity(entity: T)

    fun refreshEntities()

    fun deleteAllEntities()

    fun deleteEntity(entityId: String)
}