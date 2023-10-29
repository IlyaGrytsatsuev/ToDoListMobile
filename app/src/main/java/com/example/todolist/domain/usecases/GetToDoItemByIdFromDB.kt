package com.example.todolist.domain.usecases

import com.example.todolist.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetToDoItemByIdFromDB @Inject constructor(private val repositoryDB: DatabaseRepository) {
    suspend fun execute(id:Int) = repositoryDB.getItemById(id)

}