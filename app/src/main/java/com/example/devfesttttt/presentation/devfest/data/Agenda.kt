package com.example.devfesttttt.presentation.devfest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "agenda_table")
data class Agenda(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "start_date_time")
    val startDateTime: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "end_date_time")
    val endDateTime: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "venue")
    val venue: String,

    ) : Serializable