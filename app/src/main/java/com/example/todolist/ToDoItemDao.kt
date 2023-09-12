package com.example.todolist

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoItemDao {
    @Query("select * from to_do_item")
    fun getToDoItems() : Flow<List<ToDoItem>>

    @Query("select * from to_do_item where id = (:id)")
    fun getItemById(id:Int) : ToDoItem
}