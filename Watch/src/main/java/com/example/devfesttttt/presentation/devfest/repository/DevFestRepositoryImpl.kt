package com.example.devfesttttt.presentation.devfest.repository

import androidx.lifecycle.LiveData
import com.example.devfesttttt.presentation.BaseRepositoryImpl
import com.example.devfesttttt.presentation.UserPreferences
import com.example.devfesttttt.presentation.devfest.data.Agenda
import com.example.devfesttttt.presentation.devfest.data.Session
import com.example.devfesttttt.presentation.devfest.data.Speaker
import com.example.devfesttttt.presentation.devfest.data.database.AgendaDatabase
import com.example.devfesttttt.presentation.devfest.data.database.SessionDatabase
import com.example.devfesttttt.presentation.devfest.data.database.SpeakerDatabase
import javax.inject.Inject

class DevFestRepositoryImpl @Inject constructor(
    dataStore: UserPreferences,
    private val agendaDataBase: AgendaDatabase,
    private val sessionDatabase: SessionDatabase,
    private val speakerDatabase: SpeakerDatabase
): DevFestRepository, BaseRepositoryImpl(dataStore){

    override fun getAgenda(): LiveData<List<Agenda>> =
        agendaDataBase.agendaDAO().getAgenda()

    override fun getAgendaById(id: Int): LiveData<Agenda> =
        agendaDataBase.agendaDAO().getAgendaById(id)

    override fun updateAgenda(agenda: Agenda) {
        agendaDataBase.agendaDAO().updateAgenda(agenda)
    }

    override fun insertAgenda(agenda: Agenda) {
        agendaDataBase.agendaDAO().insertAgenda(agenda)
    }

    override fun deleteAgenda(agenda: Agenda) {
        agendaDataBase.agendaDAO().deleteAgenda(agenda)
    }

    override fun nukeAgendaTable() {
        agendaDataBase.agendaDAO().nukeAgendaTable()
    }

    override fun getSession(): LiveData<List<Session>> =
        sessionDatabase.sessionDao().getSession()

    override fun getSessionById(id: Int): LiveData<Session> =
        sessionDatabase.sessionDao().getSessionById(id)

    override fun updateSession(session: Session) {
        sessionDatabase.sessionDao().updateSession(session)
    }

    override fun insertSession(session: Session) {
        sessionDatabase.sessionDao().insertSession(session)
    }

    override fun deleteSession(session: Session) {
        sessionDatabase.sessionDao().deleteSession(session)
    }

    override fun nukeSessionTable() {
        sessionDatabase.sessionDao().nukeSessionTable()
    }

    override fun getSpeakers(): LiveData<List<Speaker>> =
        speakerDatabase.speakerDao().getSpeaker()

    override fun getSpeakerById(id: Int): LiveData<Speaker> =
        speakerDatabase.speakerDao().getSpeakerById(id)

    override fun updateSpeaker(speaker: Speaker) {
        speakerDatabase.speakerDao().updateSpeaker(speaker)
    }

    override fun insertSpeaker(speaker: Speaker) {
        speakerDatabase.speakerDao().insertSpeaker(speaker)
    }

    override fun deleteSpeaker(speaker: Speaker) {
        speakerDatabase.speakerDao().deleteSpeaker(speaker)
    }

    override fun nukeSpeakerTable() {
        speakerDatabase.speakerDao().nukeSpeakerTable()
    }


}