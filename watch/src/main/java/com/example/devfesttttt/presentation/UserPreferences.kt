package com.example.devfesttttt.presentation

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "USER_PREFERENCES")

class UserPreferences(private val context: Context) {

    private object PreferencesKeys {
        val KEY_USER_EMAIL = stringPreferencesKey("KEY_USER_EMAIL")
        val KEY_USER_NAME = stringPreferencesKey("KEY_USER_NAME")
        val KEY_USER_SIGNED_IN = booleanPreferencesKey("KEY_USER_SIGNED_IN")
    }

    val userEmail: Flow<String>
        get() = context.dataStore.data.map { preference ->
            preference[PreferencesKeys.KEY_USER_EMAIL] ?: ""
        }

    suspend fun userEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_USER_EMAIL] = email
        }
    }

    val userName: Flow<String>
        get() = context.dataStore.data.map { preference ->
            preference[PreferencesKeys.KEY_USER_NAME] ?: ""
        }

    suspend fun userName(fName: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_USER_NAME] = fName
        }
    }

    val userSignedIn: Flow<Boolean>
        get() = context.dataStore.data.map { preference ->
            preference[PreferencesKeys.KEY_USER_SIGNED_IN] ?: false
        }

    suspend fun userSignedIn(userSignedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_USER_SIGNED_IN] = userSignedIn
        }
    }
}