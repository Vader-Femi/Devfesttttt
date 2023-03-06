package com.example.devfesttttt.presentation

import androidx.room.TypeConverter
import com.example.devfesttttt.presentation.devfest.data.SessionTalk
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object RoomConverters {

    private val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a", Locale.ENGLISH)
    //Or dd-MM-yyyy HH:mm:ss


    @TypeConverter
    @JvmStatic
    fun stringToDate(dateLong: String): LocalDateTime {
        return LocalDateTime.parse(dateLong, formatter)
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(date: LocalDateTime): String {
        return date.format(formatter)
    }

    @TypeConverter
    @JvmStatic
    fun listOfSessionTalkToGson(list: List<SessionTalk>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SessionTalk>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun gsonToListOfSessionTalk(value: String): List<SessionTalk> {
        val gson = Gson()
        val type = object : TypeToken<List<SessionTalk>>() {}.type
        return gson.fromJson(value, type)
    }

}