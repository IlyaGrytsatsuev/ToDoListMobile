package com.example.todolist.domain.usecases

import com.example.todolist.domain.repository.TokenRepository
import javax.inject.Inject

class DeleteToken @Inject constructor(private val tokenRepository: TokenRepository){

    suspend fun execute(){
        tokenRepository.deleteToken()
    }
}