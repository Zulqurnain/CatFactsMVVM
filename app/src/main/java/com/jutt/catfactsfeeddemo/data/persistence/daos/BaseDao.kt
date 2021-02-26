package com.jutt.catfactsfeeddemo.data.persistence.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: T)

    @Update
    fun update(vararg items: T)

    @Delete
    fun delete(vararg items: T)

}