package com.example.devfesttttt.presentation.authentication.repository

import com.example.devfesttttt.presentation.BaseRepositoryImpl
import com.example.devfesttttt.presentation.UserPreferences
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    dataStore: UserPreferences
): AuthenticationRepository, BaseRepositoryImpl(dataStore)