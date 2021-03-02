package com.jutt.catfactsfeeddemo.data.repositories

import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import com.jutt.catfactsfeeddemo.data.persistence.AppDatabase
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.withLock

@Singleton
class DatabaseRepository @Inject constructor(private val database: AppDatabase) {
    fun catFactsDao() = database.catFactsDao()

    fun getAllFacts()  = catFactsDao().loadAll()

    suspend fun clearAllFacts() {
        catFactsDao().clearTable()
    }
    suspend fun upsertCatFact(vararg items: AnimalFact){
        catFactsDao().insertOrUpdate(items.toList())
    }
}