package com.jutt.catfactsfeeddemo.data.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jutt.catfactsfeeddemo.data.models.AnimalFact

@Dao
interface AnimalFactsDao : BaseDao<AnimalFact> {
    @Query("SELECT * FROM AnimalFact WHERE id = :id LIMIT 1")
    fun loadById(id: Long): AnimalFact

    @Query("DELETE FROM AnimalFact WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("SELECT * FROM AnimalFact")
    fun loadAll(): List<AnimalFact>

    @Insert
    fun insertIfNotExist(vararg items: AnimalFact)

    @Query("DELETE FROM AnimalFact")
    fun clearTable()
}
