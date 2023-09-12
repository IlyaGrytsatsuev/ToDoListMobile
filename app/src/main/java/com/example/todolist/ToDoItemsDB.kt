package com.example.todolist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ToDoItem::class], version = 1)
abstract class ToDoItemsDB : RoomDatabase() {
    abstract val dao : ToDoItemDao
}