package com.example.devfesttttt.presentation

import kotlinx.coroutines.flow.first
import javax.inject.Inject

open class BaseRepositoryImpl @Inject constructor(
    private val dataStore: UserPreferences
): BaseRepository {

    override suspend fun userEmail(email: String) = dataStore.userEmail(email)

    override suspend fun userEmail(): String = dataStore.userEmail.first()

    override suspend fun userName(name: String) = dataStore.userName(name)

    override suspend fun userName(): String = dataStore.userName.first()

    override suspend fun userSignedIn(userSignedIn: Boolean) = dataStore.userSignedIn(userSignedIn)

    override suspend fun userSignedIn(): Boolean = dataStore.userSignedIn.first()
}