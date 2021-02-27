package com.jutt.catfactsfeeddemo.data.persistance.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import com.jutt.catfactsfeeddemo.data.persistence.AppDatabase
import com.jutt.catfactsfeeddemo.data.persistence.daos.AnimalFactsDao
import com.jutt.catfactsfeeddemo.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AnimalFactsDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: AnimalFactsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
                .setTransactionExecutor(Executors.newSingleThreadExecutor())
                .build()
        dao = database.catFactsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAnimalFactIfNotExist() = runBlocking {
        val animalFact = AnimalFact(
            id = "58e008800aac31001185ed07",
            createdAt = "2018-03-06T21:20:03.505Z",
            deleted = false,
            source = "user",
            status = AnimalFact.Status(
                sentCount = 1,
                verified = true
            ),
            text = "Wikipedia has a recording of a cat meowing, because why not?",
            type = "cat",
            updatedAt = "2020-08-23T20:20:01.611Z",
            used = false,
            user = "58e007480aac31001185ecef",
            v = 0
        )
        dao.insert(animalFact)

        dao.insertOrUpdate(animalFact)

        val allAnimalFacts = dao.loadAll().getOrAwaitValue()

        Truth.assertThat(allAnimalFacts).hasSize(1)
    }
    @Test
    fun insertAnimalFact() = runBlocking {
        val animalFact = AnimalFact(
            id = "58e008800aac31001185ed07",
            createdAt = "2018-03-06T21:20:03.505Z",
            deleted = false,
            source = "user",
            status = AnimalFact.Status(
                sentCount = 1,
                verified = true
            ),
            text = "Wikipedia has a recording of a cat meowing, because why not?",
            type = "cat",
            updatedAt = "2020-08-23T20:20:01.611Z",
            used = false,
            user = "58e007480aac31001185ecef",
            v = 0
        )
        dao.insert(animalFact)

        val allAnimalFacts = dao.loadAll().getOrAwaitValue()

        Truth.assertThat(allAnimalFacts).contains(animalFact)
    }

    @Test
    fun deleteAnimalFact() = runBlocking {
        val animalFact = AnimalFact(
            id = "58e008800aac31001185ed07",
            createdAt = "2018-03-06T21:20:03.505Z",
            deleted = false,
            source = "user",
            status = AnimalFact.Status(
                sentCount = 1,
                verified = true
            ),
            text = "Wikipedia has a recording of a cat meowing, because why not?",
            type = "cat",
            updatedAt = "2020-08-23T20:20:01.611Z",
            used = false,
            user = "58e007480aac31001185ecef",
            v = 0
        )
        dao.insert(animalFact)
        dao.delete(animalFact)

        val allAnimalFacts = dao.loadAll().getOrAwaitValue()

        Truth.assertThat(allAnimalFacts).doesNotContain(animalFact)
    }

    @Test
    fun clearTable() = runBlocking {
        val animalFact = AnimalFact(
            id = "58e008800aac31001185ed07",
            createdAt = "2018-03-06T21:20:03.505Z",
            deleted = false,
            source = "user",
            status = AnimalFact.Status(
                sentCount = 1,
                verified = true
            ),
            text = "Wikipedia has a recording of a cat meowing, because why not?",
            type = "cat",
            updatedAt = "2020-08-23T20:20:01.611Z",
            used = false,
            user = "58e007480aac31001185ecef",
            v = 0
        )
        dao.insert(animalFact)

        dao.clearTable()

        val allFacts = dao.loadAll().getOrAwaitValue()

        Truth.assertThat(allFacts).isEmpty()
    }
}