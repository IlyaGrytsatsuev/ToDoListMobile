package com.example.todolist.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.repository.ToDoItemsRepository
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.network.responseModels.FilesInfo
import com.example.todolist.repository.ApiRepository
import com.example.todolist.utils.ResponseType
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject
constructor(private val repository: ToDoItemsRepository
   // private val apiRepository: ApiRepository
) : ViewModel() {
    private val privateList =
        MutableStateFlow<List<ToDoItemEntity>>(ArrayList())
    private val privateCurrentItem =
        MutableStateFlow(ToDoItemEntity())

    val currentItem : StateFlow<ToDoItemEntity>
        get() = privateCurrentItem

    val itemsList : StateFlow<List<ToDoItemEntity>>
        get() = privateList

    var oldItem : ToDoItemEntity = ToDoItemEntity()


    init{
        getToDoItems()
    }


    fun getToDoItems() = viewModelScope.launch(Dispatchers.IO){
        repository.getItemsList()
            .collect{privateList.emit(it)}

    }

    fun getItemById(id:Int) = viewModelScope.launch(Dispatchers.IO) {
        try{
        repository.getItemById(id)
            .collect {
                privateCurrentItem.value = it
                oldItem = it.copy()
            }
        }
        catch (e: Exception){
            Log.d("getByidExc", "uncknown exception")
        }
    }

    fun addToDoItem(item : ToDoItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addItem(item)
        getToDoItems()
    }

    fun deleteItem(item : ToDoItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteItem(item)
        Log.d("exc", "deleted")
        getToDoItems()
    }


//    fun postItemsToDisk() = viewModelScope.launch(Dispatchers.IO) {
//        val list = itemsList.value
//        list.forEach {
//            val itemJsonStr = Gson().toJson(it)
//            Log.d("itemsForPost", itemJsonStr)
//
//
//        }
//    }
//    fun getItemsFromApi() = viewModelScope.launch(Dispatchers.IO) {
//        try {
//            val response = apiRepository.getFiles()
////            if(response.isSuccessful) {
////                val filesList = response.body()?.embedded?.items
////                filesList?.forEach {
////                    Log.d("networkList",it.sizes[0].url)
////                }
////            }
//
//        }
//        catch (e:HttpException){
//            Log.d("networkList","NetworkListException")
//        }
//    }

//    private fun handleResponse(response: Response<FilesInfo>): ResponseType<FilesInfo> {
//        if(response.isSuccessful){
//            return ResponseType.Success(response.body())
//        }
//        val obj = JSONObject(response.body().toString())
//        val errorMessage = obj.getString("message")
//        return ResponseType.Error(message = errorMessage)
//    }


}