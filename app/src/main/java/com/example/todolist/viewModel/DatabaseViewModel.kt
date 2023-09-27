package com.example.todolist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.repository.ToDoItemsRepository
import com.example.todolist.db.ToDoItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(private val repository: ToDoItemsRepository) : ViewModel() {
    private val privateList = MutableLiveData<List<ToDoItemEntity>>()
    private val privateCurrentItem = MutableLiveData<ToDoItemEntity>()
    val currentItem : MutableLiveData<ToDoItemEntity>
        get() = privateCurrentItem

    val itemsList : LiveData<List<ToDoItemEntity>>
        get() = privateList

    var oldItem : ToDoItemEntity = ToDoItemEntity()


    init{
        getToDoItems()
    }

    fun createNewCurrentItemInstance(){

    }
    fun getToDoItems() = viewModelScope.launch{
        repository.getItemsList()
            .collect{privateList.postValue(it)}
    }

    fun getItemById(id:Int) = viewModelScope.launch {
            repository.getItemById(id).collect {
                privateCurrentItem.postValue(it)
                oldItem = it.copy()
//                Log.d("current_item_text", it.text)
            }
    }



    fun addToDoItem(item : ToDoItemEntity) = viewModelScope.launch {
        repository.addItem(item)
        getToDoItems()
    }

    fun deleteItem(item : ToDoItemEntity) = viewModelScope.launch {
        repository.deleteItem(item)
        getToDoItems()
    }



}