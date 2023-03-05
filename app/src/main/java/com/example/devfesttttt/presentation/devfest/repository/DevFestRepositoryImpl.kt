package com.example.devfesttttt.presentation.devfest.repository

import androidx.lifecycle.LiveData
import com.example.devfesttttt.presentation.BaseRepositoryImpl
import com.example.devfesttttt.presentation.UserPreferences
import com.example.devfesttttt.presentation.devfest.data.Agenda
import com.example.devfesttttt.presentation.devfest.data.database.AgendaDatabase
import javax.inject.Inject

class DevFestRepositoryImpl @Inject constructor(
    dataStore: UserPreferences,
    private val dataBase: AgendaDatabase
): DevFestRepository, BaseRepositoryImpl(dataStore){

    override fun getAgenda(): LiveData<List<Agenda>> =
        dataBase.agendaDAO().getAgenda()

    override fun getAgendaById(id: Int): LiveData<Agenda> =
        dataBase.agendaDAO().getAgendaById(id)

    override fun updateAgenda(agenda: Agenda) {
        dataBase.agendaDAO().updateAgenda(agenda)
    }

    override fun insertAgenda(agenda: Agenda) {
        dataBase.agendaDAO().insertAgenda(agenda)
    }

    override fun deleteAgenda(agenda: Agenda) {
        dataBase.agendaDAO().deleteAgenda(agenda)
    }


}