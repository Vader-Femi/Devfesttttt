package com.example.devfesttttt.presentation

interface BaseRepository{

    suspend fun userEmail(email: String)

    suspend fun userEmail(): String

    suspend fun userName(name: String)

    suspend fun userName(): String

    suspend fun userSignedIn(userSignedIn: Boolean)

    suspend fun userSignedIn(): Boolean
}