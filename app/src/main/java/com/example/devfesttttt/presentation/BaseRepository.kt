package com.example.devfesttttt.presentation

interface BaseRepository{

    suspend fun userEmail(email: String)

    suspend fun userEmail(): String

    suspend fun userFName(fName: String)

    suspend fun userFName(): String

    suspend fun userLName(lName: String)

    suspend fun userLName(): String
}