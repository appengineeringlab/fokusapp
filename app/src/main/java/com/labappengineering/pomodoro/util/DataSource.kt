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

    fun getEntity(taskId: String, callback: GetEntityCallback<T>)

    fun saveEntity(task: T)

    fun refreshEntities()

    fun deleteAllEntities()

    fun deleteEntity(entityId: String)
}