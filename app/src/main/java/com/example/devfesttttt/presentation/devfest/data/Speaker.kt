package com.example.devfesttttt.presentation.devfest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devfesttttt.R

@Entity(tableName = "speaker_table")
data class Speaker(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "speaker_name")
    val speakerName: String,

    @ColumnInfo(name = "speaker_profile_pic")
    val speakerProfilePic: String,

    @ColumnInfo(name = "speaker_about")
    val speakerAbout: String,

    @ColumnInfo(name = "speaker_facebook")
    val speakerFacebook: String,

    @ColumnInfo(name = "speaker_twitter")
    val speakerTwitter: String,

    @ColumnInfo(name = "speaker_linkedin")
    val speakerLinkedin: String
)