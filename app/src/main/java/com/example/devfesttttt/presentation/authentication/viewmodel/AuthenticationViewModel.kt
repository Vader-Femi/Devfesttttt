package com.example.devfesttttt.presentation.authentication.viewmodel

import com.example.devfesttttt.presentation.BaseViewModel
import com.example.devfesttttt.presentation.authentication.repository.AuthenticationRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    repository: AuthenticationRepositoryImpl
) : BaseViewModel(repository) {


}