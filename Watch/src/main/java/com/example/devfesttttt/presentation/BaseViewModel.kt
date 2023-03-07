package com.example.devfesttttt.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val repository: BaseRepositoryImpl,
) : ViewModel() {

    fun userEmail(email: String) = viewModelScope.launch {
        repository.userEmail(email)
    }

    suspend fun userEmail(): String = repository.userEmail()

    fun userFName(fName: String) = viewModelScope.launch {
        repository.userFName(fName)
    }

    suspend fun userFName(): String = repository.userFName()

    fun userLName(lName: String) = viewModelScope.launch {
        repository.userLName(lName)
    }

    suspend fun userSignedIn(): Boolean = repository.userSignedIn()

    fun userSignedIn(userSignedIn: Boolean) = viewModelScope.launch {
        repository.userSignedIn(userSignedIn)
    }
}
