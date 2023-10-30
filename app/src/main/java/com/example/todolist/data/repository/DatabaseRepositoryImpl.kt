package com.example.todolist.data.repository

import com.example.todolist.data.db.ToDoItemDao
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DatabaseRepositoryImpl (private val dao: ToDoItemDao) : DatabaseRepository {
    override suspend fun getItemsList() : List<ToDoItemEntity>
    = dao.getToDoItems().firstOrNull() ?: emptyList()

    override suspend fun getItemById(id:Int): ToDoItemEntity
    = dao.getItemById(id).firstOrNull() ?: ToDoItemEntity()

    override suspend fun addItem(item : ToDoItemEntity) = dao.addItem(item)

    override suspend fun deleteItem(item : ToDoItemEntity) = dao.deleteItem(item)
}