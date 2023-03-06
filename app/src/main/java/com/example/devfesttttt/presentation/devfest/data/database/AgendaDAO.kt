package com.example.devfesttttt.presentation.devfest.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.devfesttttt.presentation.devfest.data.Agenda

@Dao
interface AgendaDAO {

    @Query("SELECT * FROM agenda_table ORDER BY id ASC")
    fun getAgenda(): LiveData<List<Agenda>>

    @Query("SELECT * FROM agenda_table where id = :id LIMIT 1")
    fun getAgendaById(id: Int): LiveData<Agenda>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAgenda(agenda: Agenda)

    @Update
    fun updateAgenda(agenda: Agenda)

    @Delete
    fun deleteAgenda(agenda: Agenda)

    @Query("DELETE FROM agenda_table")
    fun nukeAgendaTable()
}