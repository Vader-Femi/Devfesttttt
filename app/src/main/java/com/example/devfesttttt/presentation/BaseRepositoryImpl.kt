package com.example.devfesttttt.presentation

import kotlinx.coroutines.flow.first
import javax.inject.Inject

open class BaseRepositoryImpl @Inject constructor(
    private val dataStore: UserPreferences
): BaseRepository {

    override suspend fun userEmail(email: String) = dataStore.userEmail(email)

    override suspend fun userEmail(): String = dataStore.userEmail.first()

    override suspend fun userFName(fName: String) = dataStore.userFName(fName)

    override suspend fun userFName(): String = dataStore.userFName.first()

    override suspend fun userLName(lName: String) = dataStore.userLName(lName)

    override suspend fun userLName(): String = dataStore.userLName.first()
}