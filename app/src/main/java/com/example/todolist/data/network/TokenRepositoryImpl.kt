package com.example.todolist.data.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.todolist.domain.models.OauthToken
import com.example.todolist.domain.repository.TokenRepository
import com.example.todolist.utils.Constants
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_info")
class TokenRepositoryImpl @Inject constructor(val context:Context)
: TokenRepository {

    override var tokenObj:OauthToken = OauthToken("")

    override suspend fun getToken(): OauthToken {
        tokenObj = OauthToken(
            context.dataStore.data
                .map{ it[Constants.TOKEN_PREF] ?: "" }.firstOrNull()?:"")
        return tokenObj
    }


    override suspend fun saveToken(tokenObj:OauthToken){
        context.dataStore.edit{
            it[Constants.TOKEN_PREF] = tokenObj.token
        }
    }
    override suspend fun deleteToken(){
        context.dataStore.edit{
            it.clear()
        }
    }

}