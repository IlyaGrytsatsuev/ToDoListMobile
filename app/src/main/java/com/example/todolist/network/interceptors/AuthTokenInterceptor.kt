package com.example.todolist.network.interceptors

import android.util.Log
import com.example.todolist.network.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor (val tokenManager:TokenManager) : Interceptor {
    val TOKEN_HEADER = "Authorization"
    var authToken = ""
    override  fun intercept(chain: Interceptor.Chain): Response {
        authToken = tokenManager.token
        Log.d("networkListToken", authToken)
        val originRequest = chain.request()
        val requiresToken = originRequest.headers["require_OAuth"] == "true"
        val resultRequest = originRequest.newBuilder()
        if(!requiresToken) {
            resultRequest.addHeader(TOKEN_HEADER, authToken)
            resultRequest.removeHeader("require_OAuth")
        }

        return chain.proceed(resultRequest.build())
    }
}