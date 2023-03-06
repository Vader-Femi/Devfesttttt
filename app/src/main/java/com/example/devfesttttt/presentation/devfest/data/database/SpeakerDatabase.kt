package com.example.devfesttttt.presentation.devfest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.devfesttttt.presentation.RoomConverters
import com.example.devfesttttt.presentation.devfest.data.Speaker

@Database(entities = [Speaker::class], version = 1, exportSchema = false)
abstract class SpeakerDatabase: RoomDatabase() {

    abstract fun speakerDao(): SpeakerDAO

    companion object {

        private const val databaseName = "com.example.devfesttttt.speaker.database"

        private var instance: SpeakerDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            SpeakerDatabase::class.java,
            databaseName
        ).fallbackToDestructiveMigration()
            .build()
    }
}