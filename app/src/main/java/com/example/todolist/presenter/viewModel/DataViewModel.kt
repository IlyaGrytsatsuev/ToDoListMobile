package com.example.todolist.presenter.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.models.OauthToken
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.domain.usecases.AddToDoItem
import com.example.todolist.domain.usecases.DeleteToDoItemFromDB
import com.example.todolist.domain.usecases.GetItemsListFromApi
import com.example.todolist.domain.usecases.GetOAuthToken
import com.example.todolist.domain.usecases.GetToDoItemByIdFromDB
import com.example.todolist.domain.usecases.GetToDoItemsFromDB
import com.example.todolist.domain.usecases.SaveOAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject
constructor(
            @ApplicationContext private val context: Context,
            private val getToDoItemFromDB: GetToDoItemsFromDB,
            private val getToDoItemByIdFromDB: GetToDoItemByIdFromDB,
            private val addToDoItemFromDB: AddToDoItem,
            private val deleteToDoItemFromDB: DeleteToDoItemFromDB,
            private val getOAuthToken: GetOAuthToken,
            private val saveOauthToken: SaveOAuthToken,
            private val deleteOAuthToken: GetOAuthToken,
            private val getItemsListFromApi: GetItemsListFromApi

) : ViewModel() {


    private val privateList =
        MutableStateFlow<List<ToDoItemEntity>>(ArrayList())
    private val privateCurrentItem =
        MutableStateFlow(ToDoItemEntity())

    val currentItem : StateFlow<ToDoItemEntity> = privateCurrentItem

    val itemsList : StateFlow<List<ToDoItemEntity>> = privateList

    private val privateToken: MutableStateFlow<OauthToken>
    = MutableStateFlow(OauthToken(""))

    private val privateIsExceptionThrown = MutableStateFlow(false)

    val isExceptionThrown: StateFlow<Boolean> = privateIsExceptionThrown

    val token : StateFlow<OauthToken> = privateToken

    var oldItem : ToDoItemEntity = ToDoItemEntity()


    init{
        getToDoItems()
    }

    fun getToDoItems() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                privateList.value = getToDoItemFromDB.execute()
            } catch (e: Exception) {
                privateIsExceptionThrown.value = true
            }
        }
    }

    fun getItemById(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                privateCurrentItem.value = getToDoItemByIdFromDB.execute(id)
                oldItem = privateCurrentItem.value.copy()
            }
            catch (e: Exception) {
                privateIsExceptionThrown.value = true
            }
        }
    }


    fun addToDoItem(item : ToDoItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addToDoItemFromDB.execute(item)
                getToDoItems()
            }
            catch (e: Exception) {
                privateIsExceptionThrown.value = true
            }
        }
    }

    fun deleteItem(item : ToDoItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteToDoItemFromDB.execute(item)
                getToDoItems()
            }
            catch (e: Exception) {
                privateIsExceptionThrown.value = true
            }
        }
    }

    fun getOAuthToken() {
        viewModelScope.launch {
            try {
                privateToken.value = getOAuthToken.execute()
            }
            catch (e: Exception) {
                privateIsExceptionThrown.value = true
            }
        }

    }

    fun saveOauthToken(token: OauthToken) {
        viewModelScope.launch {
            try {
                saveOauthToken.execute(token)
            }
            catch (e: Exception) {
                privateIsExceptionThrown.value = true
            }
        }
    }
    fun deleteOauthToken() {
        viewModelScope.launch {
            try {
                deleteOAuthToken.execute()
            }
            catch (e: Exception) {
                privateIsExceptionThrown.value = true
            }
        }
    }

    fun setExceptionNotThrown(){
        privateIsExceptionThrown.value = false
    }

    fun getItemsFromApi() = viewModelScope.launch(Dispatchers.IO){
        try {
            val list = getItemsListFromApi.execute()
            Log.d("networkList", list.toString())
        }
        catch (e:Exception){
            Log.d("networkList", "${e.message}")
        }
    }

}