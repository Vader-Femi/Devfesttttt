package com.example.devfesttttt.presentation.devfest.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.devfesttttt.presentation.devfest.data.Speaker

@Dao
interface SpeakerDAO {

    @Query("SELECT * FROM speaker_table ORDER BY id ASC")
    fun getSpeaker(): LiveData<List<Speaker>>

    @Query("SELECT * FROM speaker_table where id = :id LIMIT 1")
    fun getSpeakerById(id: Int): LiveData<Speaker>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpeaker(speaker: Speaker)

    @Update
    fun updateSpeaker(speaker: Speaker)

    @Delete
    fun deleteSpeaker(speaker: Speaker)

    @Query("DELETE FROM speaker_table")
    fun nukeSpeakerTable()
}