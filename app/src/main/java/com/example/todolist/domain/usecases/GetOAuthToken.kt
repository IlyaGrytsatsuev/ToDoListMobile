package com.example.todolist.domain.usecases

import androidx.datastore.core.DataStore
import com.example.todolist.domain.repository.TokenRepository
import javax.inject.Inject

class GetOAuthToken @Inject constructor(private val tokenRepository: TokenRepository) {

    suspend fun execute() = tokenRepository.getToken()

}