package com.example.todolist.data.repository

import com.example.todolist.data.network.DiskApiService
import com.example.todolist.data.network.responseModels.Item
import com.example.todolist.domain.repository.ApiRepository
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiService: DiskApiService) : ApiRepository {
    override suspend fun getItemsList(): List<Item>? {
        val response = apiService.getFilesList()
        return response.body()?.embedded?.items
    }
}