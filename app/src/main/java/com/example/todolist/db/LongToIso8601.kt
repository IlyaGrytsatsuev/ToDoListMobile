package com.example.todolist.db

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class LongToIso8601 {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    @TypeConverter
    fun fromApiDate(value:String) : Long{
        val date = dateFormat.parse(value)
        return date?.time ?: 0
    }

    @TypeConverter
    fun fromZonedDateToApiDate(value:Long):String {
        val date = Date(value)
        return  dateFormat.format(date)
    }
}