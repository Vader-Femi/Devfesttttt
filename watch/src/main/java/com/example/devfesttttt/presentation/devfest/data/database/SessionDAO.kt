package com.example.devfesttttt.presentation.devfest.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.devfesttttt.presentation.devfest.data.Session

@Dao
interface SessionDAO {

    @Query("SELECT * FROM session_table ORDER BY id ASC")
    fun getSession(): LiveData<List<Session>>

    @Query("SELECT * FROM session_table where id = :id LIMIT 1")
    fun getSessionById(id: Int): LiveData<Session>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSession(session: Session)

    @Update
    fun updateSession(session: Session)

    @Delete
    fun deleteSession(session: Session)

    @Query("DELETE FROM session_table")
    fun nukeSessionTable()
}