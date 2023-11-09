package com.example.todolist.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.todolist.domain.models.ToDoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoItemDao {
    @Query("select * from to_do_item")
    suspend fun getToDoItems() : MutableList<ToDoItemEntity>
    @Query("select * from to_do_item where id = :id")
    fun getItemById(id:Int) : Flow<ToDoItemEntity>
    @Upsert
    suspend fun addItem(item : ToDoItemEntity)
    @Delete
    suspend fun deleteItem(item: ToDoItemEntity)
}