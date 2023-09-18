package com.example.todolist.repository

import com.example.todolist.db.ToDoItemDao
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.db.ToDoItemsDB
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton


class ToDoItemsRepository @Inject constructor(private val dao: ToDoItemDao) {

    fun getItemsList() = dao.getToDoItems()

    fun getItemById(id:Int) = dao.getItemById(id)

    suspend fun addItem(item : ToDoItemEntity) = dao.addItem(item)

    suspend fun deleteItem(item : ToDoItemEntity) = dao.deleteItem(item)



}
