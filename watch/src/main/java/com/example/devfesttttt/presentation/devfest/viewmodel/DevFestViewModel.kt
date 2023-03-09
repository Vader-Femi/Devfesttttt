package com.example.devfesttttt.presentation.devfest.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.devfesttttt.presentation.BaseViewModel
import com.example.devfesttttt.presentation.devfest.data.Agenda
import com.example.devfesttttt.presentation.devfest.data.Session
import com.example.devfesttttt.presentation.devfest.data.Speaker
import com.example.devfesttttt.presentation.devfest.repository.DevFestRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DevFestViewModel @Inject constructor(
    private val repository: DevFestRepositoryImpl,
    application: Application
): BaseViewModel(repository, application){

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

    fun nukeAgendaTable() {
        repository.nukeAgendaTable()
    }

    fun getSession(): LiveData<List<Session>> =
        repository.getSession()

    fun getSessionById(id: Int): LiveData<Session> =
        repository.getSessionById(id)

    fun updateSession(Session: Session) {
        repository.updateSession(Session)
    }

    fun insertSession(Session: Session) {
        repository.insertSession(Session)
    }

    fun deleteSession(Session: Session) {
        repository.deleteSession(Session)
    }

    fun nukeSessionTable() {
        repository.nukeSessionTable()
    }

    fun getSpeakers(): LiveData<List<Speaker>> =
        repository.getSpeakers()

    fun getSpeakerById(id: Int): LiveData<Speaker> =
        repository.getSpeakerById(id)

    fun updateSpeaker(Speaker: Speaker) {
        repository.updateSpeaker(Speaker)
    }

    fun insertSpeaker(Speaker: Speaker) {
        repository.insertSpeaker(Speaker)
    }

    fun deleteSpeaker(Speaker: Speaker) {
        repository.deleteSpeaker(Speaker)
    }

    fun nukeSpeakerTable() {
        repository.nukeSpeakerTable()
    }

}