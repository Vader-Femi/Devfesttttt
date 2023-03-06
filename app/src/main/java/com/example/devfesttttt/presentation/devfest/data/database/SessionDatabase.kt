package com.example.devfesttttt.presentation.devfest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.devfesttttt.presentation.RoomConverters
import com.example.devfesttttt.presentation.devfest.data.Session

@Database(entities = [Session::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class SessionDatabase: RoomDatabase() {

    abstract fun sessionDao(): SessionDAO

    companion object {

        private const val databaseName = "com.example.devfesttttt.session.database"

        private var instance: SessionDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            SessionDatabase::class.java,
            databaseName
        ).fallbackToDestructiveMigration()
            .build()
    }
}