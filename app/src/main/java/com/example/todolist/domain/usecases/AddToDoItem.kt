package com.example.todolist.domain.usecases

import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddToDoItem @Inject constructor(val repositoryDB: DatabaseRepository) {
    suspend fun execute(item: ToDoItemEntity){
        repositoryDB.addItem(item)
    }
}