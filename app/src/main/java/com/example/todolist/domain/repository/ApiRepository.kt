package com.example.todolist.domain.repository

import com.example.todolist.data.network.responseModels.Item

interface ApiRepository {
    suspend fun getItemsList():List<Item>?
}