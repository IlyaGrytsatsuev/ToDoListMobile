package com.example.todolist.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val BASE_URL = "https://cloud-api.yandex.net/v1/disk/"
    val TOKEN_PREF = stringPreferencesKey("token_pref")
    const val APP_PATH = "di"

}