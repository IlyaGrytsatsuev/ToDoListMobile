package com.example.todolist.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.Calendar
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let{Date(it)}
    }
    @TypeConverter
    fun fromDate(date: Date?): Long?{
        return date?.time
    }

}