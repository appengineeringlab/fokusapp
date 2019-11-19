package com.labappengineering.fokus.util

import androidx.room.*

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(obj: T) : Int

}
