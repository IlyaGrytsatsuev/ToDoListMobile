package com.example.todolist.domain.usecases

import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteToDoItemFromDB @Inject constructor(private val repositoryDB: DatabaseRepository){

    suspend fun execute(item: ToDoItemEntity){
        repositoryDB.deleteItem(item)
    }
}