package com.example.todolist.data.repository

import com.example.todolist.data.network.DiskApiService
import com.example.todolist.data.network.responseModels.Item
import com.example.todolist.domain.repository.ApiRepository
import retrofit2.HttpException
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiService: DiskApiService) : ApiRepository {
    override suspend fun getItemsList(): List<Item>? {
        val response = apiService.getFilesList()//todo mapping domain model
        return response.body()?.embedded?.items
    }

    override suspend fun createAppPackage() {
        val response = apiService.createApplicationFolder()
        if (!response.isSuccessful)
            throw HttpException(response)
    }
}