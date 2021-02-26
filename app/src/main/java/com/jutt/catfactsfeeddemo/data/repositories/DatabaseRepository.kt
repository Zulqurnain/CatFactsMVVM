package com.jutt.catfactsfeeddemo.data.repositories

import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import com.jutt.catfactsfeeddemo.data.persistence.AppDatabase
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.withLock

@Singleton
class DatabaseRepository @Inject constructor(private val database: AppDatabase) {

    private val reentrantLock: ReentrantLock = ReentrantLock()

    fun catFactsDao() = database.catFactsDao()

    fun clearAllFacts() {
        reentrantLock.withLock {
            catFactsDao().clearTable()
        }
    }

    fun addCatFact(facts: AnimalFact){
        reentrantLock.withLock {
            catFactsDao().insert(facts)
        }
    }
    fun addAllFacts(facts: List<AnimalFact>){
        reentrantLock.withLock {
            catFactsDao().insert(*facts.toTypedArray())
        }
    }

}