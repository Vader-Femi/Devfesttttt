package com.example.devfesttttt.presentation.devfest.viewmodel

import androidx.lifecycle.LiveData
import com.example.devfesttttt.presentation.BaseViewModel
import com.example.devfesttttt.presentation.devfest.data.Agenda
import com.example.devfesttttt.presentation.devfest.repository.DevFestRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DevFestViewModel @Inject constructor(
    private val repository: DevFestRepositoryImpl
): BaseViewModel(repository){

    fun getAgenda(): LiveData<List<Agenda>> =
        repository.getAgenda()

    fun getAgendaById(id: Int): LiveData<Agenda> =
        repository.getAgendaById(id)

    fun updateAgenda(agenda: Agenda) {
        repository.updateAgenda(agenda)
    }

    fun insertAgenda(agenda: Agenda) {
        repository.insertAgenda(agenda)
    }

    fun deleteAgenda(agenda: Agenda) {
        repository.deleteAgenda(agenda)
    }

}