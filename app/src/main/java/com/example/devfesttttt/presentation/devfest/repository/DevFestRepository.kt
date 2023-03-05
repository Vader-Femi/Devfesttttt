package com.example.devfesttttt.presentation.devfest.repository

import androidx.lifecycle.LiveData
import com.example.devfesttttt.presentation.devfest.data.Agenda
import java.time.LocalDate

interface DevFestRepository{
    abstract fun getAgenda(): LiveData<List<Agenda>>

    abstract fun getAgendaById(id: Int): LiveData<Agenda>

    abstract fun updateAgenda(agenda: Agenda)

    abstract fun insertAgenda(agenda: Agenda)

    abstract fun deleteAgenda(agenda: Agenda)
}