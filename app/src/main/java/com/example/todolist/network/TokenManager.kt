package com.example.todolist.network

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.todolist.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_info")
class TokenManager (val context: Context ) {
    var token = ""
    lateinit var tokenFlow:Flow<String>

    fun extractToken() =
        context.dataStore.data
            .map{ it[com.example.todolist.utils.Constants.TOKEN_PREF] ?: "" }


    suspend fun saveToken(token:String){
        context.dataStore.edit{
            it[Constants.TOKEN_PREF] = token
        }
    }
    suspend fun deleteToken(){
        context.dataStore.edit{
            it.clear()
        }
    }
}