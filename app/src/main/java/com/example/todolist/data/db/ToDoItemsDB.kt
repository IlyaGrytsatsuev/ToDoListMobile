package com.example.todolist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.domain.models.ToDoItemEntity

@Database(entities = [ToDoItemEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ToDoItemsDB : RoomDatabase() {
    abstract fun getDao() : ToDoItemDao
}