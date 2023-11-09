package com.example.todolist.domain.usecases

import com.example.todolist.domain.repository.ApiRepository
import javax.inject.Inject

class CreateAppPackageInDisk @Inject constructor(private val apiRepository: ApiRepository) {

    suspend fun execute(){
        apiRepository.createAppPackage()
    }
}