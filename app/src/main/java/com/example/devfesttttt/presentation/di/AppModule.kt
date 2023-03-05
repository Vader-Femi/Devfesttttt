package com.example.devfesttttt.presentation.di

import android.app.Application
import com.example.devfesttttt.presentation.UserPreferences
import com.example.devfesttttt.presentation.devfest.data.database.AgendaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(application: Application): UserPreferences {
        return UserPreferences(application)
    }

    @Provides
    @Singleton
    fun provideAgendaDatabase(application: Application): AgendaDatabase {
        return AgendaDatabase.invoke(application)
    }

}