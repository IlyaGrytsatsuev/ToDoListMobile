package com.example.todolist.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoItemDao {
    @Query("select * from to_do_item")
    fun getToDoItems() : Flow<MutableList<ToDoItemEntity>>
    @Query("select * from to_do_item where id = :id")
    fun getItemById(id:Int) : Flow<ToDoItemEntity>
    @Upsert
    suspend fun addItem(item : ToDoItemEntity)
    @Delete
    suspend fun deleteItem(item: ToDoItemEntity)
}