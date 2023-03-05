package com.example.devfesttttt.presentation

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object RoomConverters {

    private val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm:ss a", Locale.ENGLISH)
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

    //your code
//    val dates = /*"20 Aug 2012"*/ "20120820"
//    val datess = LocalDate.parse(dates, DateTimeFormatter.BASIC_ISO_DATE)
//    println(datess)


    // your example Strings
//    val dateFirst = "20 Aug 2012"
//    val dateSecond = "12/16/2020 12:00:00 AM"
//    // you need two different formatters here, your Strings differ in format and content
//    val firstFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
//    val secondFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH)
//    // then use those according to what you want to parse
//    val localDate = LocalDate.parse(dateFirst, firstFormatter)
//    val localDateTime = LocalDateTime.parse(dateSecond, secondFormatter)
//    // use the built-in formatters for output
//    println(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
//    println(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))


//    LocalDate date = LocalDate.of(1988, 5, 5);
//    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MMMM yyyy", Locale.ENGLISH);
//    String output = dtf.format(date);
//    System.out.println(output);

}