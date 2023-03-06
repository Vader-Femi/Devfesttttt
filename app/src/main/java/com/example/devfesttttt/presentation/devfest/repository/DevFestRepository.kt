package com.example.devfesttttt.presentation.devfest.repository

import androidx.lifecycle.LiveData
import com.example.devfesttttt.presentation.devfest.data.Agenda
import com.example.devfesttttt.presentation.devfest.data.Session
import com.example.devfesttttt.presentation.devfest.data.Speaker

interface DevFestRepository{

    abstract fun getAgenda(): LiveData<List<Agenda>>

    abstract fun getAgendaById(id: Int): LiveData<Agenda>

    abstract fun updateAgenda(agenda: Agenda)

    abstract fun insertAgenda(agenda: Agenda)

    abstract fun deleteAgenda(agenda: Agenda)

    abstract fun nukeAgendaTable()

    abstract fun getSession(): LiveData<List<Session>>

    abstract fun getSessionById(id: Int): LiveData<Session>

    abstract fun updateSession(session: Session)

    abstract fun insertSession(session: Session)

    abstract fun deleteSession(session: Session)

    abstract fun nukeSessionTable()

    abstract fun getSpeakers(): LiveData<List<Speaker>>

    abstract fun getSpeakerById(id: Int): LiveData<Speaker>

    abstract fun updateSpeaker(speaker: Speaker)

    abstract fun insertSpeaker(speaker: Speaker)

    abstract fun deleteSpeaker(speaker: Speaker)

    abstract fun nukeSpeakerTable()
}