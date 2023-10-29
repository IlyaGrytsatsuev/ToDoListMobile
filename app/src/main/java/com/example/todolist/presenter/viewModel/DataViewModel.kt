package com.example.todolist.presenter.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.models.OauthToken
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.domain.usecases.AddToDoItem
import com.example.todolist.domain.usecases.DeleteToDoItemFromDB
import com.example.todolist.domain.usecases.GetOAuthToken
import com.example.todolist.domain.usecases.GetToDoItemByIdFromDB
import com.example.todolist.domain.usecases.GetToDoItemsFromDB
import com.example.todolist.domain.usecases.SaveOAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
            private val deleteOAuthToken: GetOAuthToken
) : ViewModel() {


    private val privateList =
        MutableStateFlow<List<ToDoItemEntity>>(ArrayList())
    private val privateCurrentItem =
        MutableStateFlow(ToDoItemEntity())

    val currentItem : StateFlow<ToDoItemEntity>
        get() = privateCurrentItem

    val itemsList : StateFlow<List<ToDoItemEntity>>
        get() = privateList

    private val privateToken: MutableStateFlow<OauthToken>
    = MutableStateFlow(OauthToken(""))

    val token : StateFlow<OauthToken>
        get() = privateToken

    var oldItem : ToDoItemEntity = ToDoItemEntity()


    init{
        getToDoItems()
    }

    fun getToDoItems() {
        viewModelScope.launch(Dispatchers.IO) {
            privateList.value = getToDoItemFromDB.execute()
        }
    }

    fun getItemById(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            privateCurrentItem.value = getToDoItemByIdFromDB.execute(id)
            oldItem = privateCurrentItem.value.copy()
        }
    }

    fun addToDoItem(item : ToDoItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addToDoItemFromDB.execute(item)
            getToDoItems()
        }
    }

    fun deleteItem(item : ToDoItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteToDoItemFromDB.execute(item)
            getToDoItems()
        }
    }

    fun getOAuthToken() {
        viewModelScope.launch {
            privateToken.value = getOAuthToken.execute()
        }
    }

    fun saveOauthToken(token: OauthToken) {
        viewModelScope.launch {
            saveOauthToken.execute(token)
        }
    }
    fun deleteOauthToken() {
        viewModelScope.launch {
            deleteOAuthToken.execute()
        }
    }




}