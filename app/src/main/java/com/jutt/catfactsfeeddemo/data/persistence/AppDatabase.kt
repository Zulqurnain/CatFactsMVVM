
package com.jutt.catfactsfeeddemo.data.persistence

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import com.jutt.catfactsfeeddemo.data.persistence.daos.AnimalFactsDao

@Database(
    version = 1,
    entities = [
        AnimalFact::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun catFactsDao(): AnimalFactsDao

    companion object {
        private const val DATABASE_NAME = "cat-facts-db"

        fun buildDatabase(
            context: Context,
        ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                    super.onDestructiveMigration(db)
                }
            })
            .build()
    }
}

