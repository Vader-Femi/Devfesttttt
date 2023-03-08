package com.example.devfesttttt.presentation.devfest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devfesttttt.R
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "session_table")
data class Session(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "session_name")
    val sessionName: String,

    @ColumnInfo(name = "session_icon")
    val sessionIcon: Int = R.drawable.ic_app_logo,

    @ColumnInfo(name = "session_talks")
    val sessionTalks: List<SessionTalk>

    ) : Serializable

data class SessionTalk(

    val id: Int = 0,

    val startDateTime: String,

    val endDateTime: String,

    val title: String,
) : Serializable