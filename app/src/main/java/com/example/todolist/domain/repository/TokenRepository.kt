package com.example.todolist.domain.repository

import android.media.session.MediaSession.Token
import com.example.todolist.domain.models.OauthToken

interface TokenRepository {
    suspend fun getToken():OauthToken
    suspend fun saveToken(tokenObj: OauthToken)
    suspend fun deleteToken()
}