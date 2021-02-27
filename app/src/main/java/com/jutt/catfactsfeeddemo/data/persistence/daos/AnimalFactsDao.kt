package com.jutt.catfactsfeeddemo.data.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jutt.catfactsfeeddemo.data.models.AnimalFact

@Dao
abstract class AnimalFactsDao : BaseDao<AnimalFact>() {
    @Query("SELECT * FROM AnimalFact WHERE id = :id LIMIT 1")
    abstract suspend fun loadById(id: Long): AnimalFact

    @Query("DELETE FROM AnimalFact WHERE id = :id")
    abstract suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM AnimalFact")
    abstract fun loadAll(): LiveData<List<AnimalFact>>

    @Query("DELETE FROM AnimalFact")
    abstract suspend fun clearTable()
}
