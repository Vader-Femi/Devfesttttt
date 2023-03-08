package com.example.devfesttttt.presentation.devfest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.devfesttttt.presentation.RoomConverters
import com.example.devfesttttt.presentation.devfest.data.Agenda

@Database(entities = [Agenda::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class AgendaDatabase: RoomDatabase() {

    abstract fun agendaDAO(): AgendaDAO

    companion object {

        private const val databaseName = "com.example.devfesttttt.agenda.database"

        private var instance: AgendaDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            AgendaDatabase::class.java,
            databaseName
        ).fallbackToDestructiveMigration()
            .build()
    }
}