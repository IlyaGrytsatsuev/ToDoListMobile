package com.example.todolist.presenter.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.presenter.viewModel.DataViewModel
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.YandexAuthToken
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var token: YandexAuthToken = YandexAuthToken("", 1)
    val REQUEST_LOGIN_SDK = 1
    lateinit var authOptions: YandexAuthOptions

    lateinit var sdk: YandexAuthSdk

    lateinit var loginOptionsBuilder: YandexAuthLoginOptions.Builder
    lateinit var authIntent: Intent

    private val viewModel: DataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onRestart() {
        super.onRestart()
        Log.d("life", "restart")

    }

    override fun onStop() {
        super.onStop()
        Log.d("life", "stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("life", "destroy")

    }

}