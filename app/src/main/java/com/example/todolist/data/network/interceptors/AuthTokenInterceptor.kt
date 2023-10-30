package com.example.todolist.data.network.interceptors

import com.example.todolist.domain.models.OauthToken
import com.example.todolist.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor (private val tokenRepository: TokenRepository) :
    Interceptor {
    val TOKEN_HEADER = "Authorization"
    var authToken = OauthToken("")
    override fun intercept(chain: Interceptor.Chain): Response {
        authToken = tokenRepository.tokenObj
        //Log.d("networkListToken", authToken)
        val originRequest = chain.request()
        val requiresToken = originRequest.headers["require_OAuth"] == "true"
        val resultRequest = originRequest.newBuilder()
        if(!requiresToken) {
            resultRequest.addHeader(TOKEN_HEADER, authToken.token)
            resultRequest.removeHeader("require_OAuth")
        }

        return chain.proceed(resultRequest.build())
    }
}