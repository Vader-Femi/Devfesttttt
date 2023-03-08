package com.example.devfesttttt.presentation.di

import com.example.devfesttttt.presentation.BaseRepository
import com.example.devfesttttt.presentation.BaseRepositoryImpl
import com.example.devfesttttt.presentation.authentication.repository.AuthenticationRepository
import com.example.devfesttttt.presentation.authentication.repository.AuthenticationRepositoryImpl
import com.example.devfesttttt.presentation.devfest.repository.DevFestRepository
import com.example.devfesttttt.presentation.devfest.repository.DevFestRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBaseRepository(
        baseRepositoryImpl: BaseRepositoryImpl
    ): BaseRepository

    @Binds
    @Singleton
    abstract fun bindAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindDevFestRepository(
        devFestRepositoryImpl: DevFestRepositoryImpl
    ): DevFestRepository
}