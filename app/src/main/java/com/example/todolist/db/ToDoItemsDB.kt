package com.example.todolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToDoItemEntity::class], version = 1)
@TypeConverters(DateToLongConverter::class)
abstract class ToDoItemsDB : RoomDatabase() {
    abstract fun getDao() : ToDoItemDao
}