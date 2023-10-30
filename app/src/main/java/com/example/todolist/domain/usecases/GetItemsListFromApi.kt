package com.example.todolist.domain.usecases

import com.example.todolist.data.network.responseModels.Item
import com.example.todolist.domain.repository.ApiRepository
import javax.inject.Inject

class GetItemsListFromApi @Inject constructor(private val apiRepository: ApiRepository) {

    suspend fun execute() :List<Item>? =
        apiRepository.getItemsList()

}