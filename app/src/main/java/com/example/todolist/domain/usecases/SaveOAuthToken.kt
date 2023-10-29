package com.example.todolist.domain.usecases

import com.example.todolist.domain.models.OauthToken
import com.example.todolist.domain.repository.TokenRepository
import javax.inject.Inject

class SaveOAuthToken @Inject constructor(private val tokenRepository: TokenRepository) {
    suspend fun execute(tokenObj: OauthToken){
        tokenRepository.saveToken(tokenObj)
    }
}