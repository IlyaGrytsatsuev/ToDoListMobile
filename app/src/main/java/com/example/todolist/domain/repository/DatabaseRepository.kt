package com.example.todolist.domain.repository

import com.example.todolist.domain.models.ToDoItemEntity

interface DatabaseRepository {
    suspend fun getItemsList(): List<ToDoItemEntity>

    suspend fun getItemById(id:Int): ToDoItemEntity

    suspend fun addItem(item : ToDoItemEntity)

    suspend fun deleteItem(item : ToDoItemEntity)
}