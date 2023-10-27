package com.example.todolist.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.db.LongToIso8601
import com.example.todolist.repository.ToDoItemsRepository
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.network.responseModels.FilesInfo
import com.example.todolist.network.responseModels.Item
import com.example.todolist.network.responseModels.TestResponse
import com.example.todolist.repository.ApiRepository
import com.example.todolist.utils.Constants
import com.example.todolist.utils.ItemState
import com.example.todolist.utils.ResponseType
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject
constructor(@ApplicationContext private val context: Context,
            private val repository: ToDoItemsRepository,
            private val apiRepository: ApiRepository
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

    private lateinit var  itemsRemainUnsynchronized: MutableList<ToDoItemEntity>


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
        item.hash = item.hashCode().toString()
        repository.addItem(item)
        getToDoItems()
    }

    fun deleteItem(item : ToDoItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteItem(item)
        Log.d("exc", "deleted")
        getToDoItems()
    }


//    fun uploadItemsToDisk() = viewModelScope.launch(Dispatchers.IO) {
//        val list = itemsList.value
//        list.forEach {
//            val itemJsonStr = Gson().toJson(it)
//            Log.d("itemsForPost", itemJsonStr)
//
//        }
//    }

    private fun uploadItem(item: ToDoItemEntity) = viewModelScope.launch {
        val uploadLinkReceiver = async{
            apiRepository.getUploadUrl("${Constants.APP_PATH}/${item.hash}", false)
        }
        val response = uploadLinkReceiver.await()
        Log.d("networkList", "uploadUrlResponse = $response")
        if (response.isSuccessful && response.body() != null) {
            item.state = ItemState.SYNCHRONIZED.text
            val itemJson = Gson().toJson(item)
            val file = File(context.cacheDir, "${item.hash}.txt")
            file.writeText(itemJson)
            val requestFile = file.asRequestBody("text/plain".toMediaTypeOrNull())

            val jsonFilePart =
                MultipartBody.Part.createFormData(item.hash, file.name, requestFile)
            val uploadResponse = apiRepository.uploadFile(response.body()!!.href, jsonFilePart)
            if(!uploadResponse.isSuccessful)
                throw HttpException(uploadResponse)
            Log.d("networkList", "putResponse = $response")
        }
        else
            Log.d("networkList", "respBody = $response")


    }

    private fun downloadFile(url:String) = viewModelScope.async {
        val response = apiRepository.downloadFile(url)
        if(!response.isSuccessful)
            throw HttpException(response)

        return@async response.body()

    }

    private suspend fun executeItemsResponseBody(response:Response<TestResponse>){
        val filesList = response.body()?.embedded?.items
        if (filesList != null) {
            if(filesList.isNotEmpty()) {
                synchronizeItems(filesList)
                Log.d("networkList", "received list is not empty")
            }
            else{
                Log.d("networkList", "received list is  empty")
                val localList = privateList.value.toList()
                for(item in localList) {
                    Log.d("networkList", "uploading $item")
                    uploadItem(item)
                }
            }

        }


    }

    private fun deleteItemFromDisk(hash: String) = viewModelScope.launch {
        val response = apiRepository.deleteFile(hash)
        if (!response.isSuccessful)
            throw HttpException(response)
    }


    private fun List<ToDoItemEntity>.containsItemWithHash(hash:String) : Boolean {
        for (item in this) {
            if (hash == item.hash)
                return true
        }
        return false
    }

    private fun List<ToDoItemEntity>.getItemWithHash(hash:String) : ToDoItemEntity {
        for (item in this) {
            if (hash == item.hash)
                return item
        }
        return ToDoItemEntity()
    }

    private suspend fun synchronizeItems(networkList:List<Item>){
        if(privateList.value.isNotEmpty())
        {
            itemsRemainUnsynchronized = privateList.value.toMutableList()
            synchronizeChanges(networkList)
            for(item in itemsRemainUnsynchronized){
                uploadItem(item)
            }
        }
    }


    private suspend fun updateLocalItemFromDisk(uploadUrl: String, localId: Int){
            val downloadTask =
                downloadFile(uploadUrl)
            val item = downloadTask.await()
            item!!.id = localId
            addToDoItem(item)
    }

    private fun uploadItemUpdateToDisk(itemForUpdate:ToDoItemEntity){
            deleteItemFromDisk(itemForUpdate.hash)
            uploadItem(itemForUpdate)
    }
    private suspend fun synchronizeChanges(networkList: List<Item>){
        val localList = privateList.value.toList()
        for(item in networkList){
            if(localList.containsItemWithHash(item.name)){
                val localListCurItem = localList.getItemWithHash(item.name)
                if(LongToIso8601().fromApiDate(item.modified)
                    > localListCurItem.modified) {
                    try {
                        updateLocalItemFromDisk(item.sizes[0].url, localListCurItem.id)
                        itemsRemainUnsynchronized.remove(localListCurItem)
                    }
                    catch (e:HttpException){
                        Log.d("exc", "Item download exception!!")
                    }
                }
                else {
                    try {
                        uploadItemUpdateToDisk(localListCurItem)
                        itemsRemainUnsynchronized.remove(localListCurItem)
                    }
                    catch (e:HttpException){
                        Log.d("exc", "Item update exception!!")
                    }
                }
            }
            else {
                try {
                    val downloadTask = downloadFile(item.sizes[0].url)
                    addToDoItem(downloadTask.await()!!)
                }
                catch (e:HttpException){
                    Log.d("exc", "Item download exception!!")
                }
            }

            //Log.d("networkList",it.sizes[0].url)

        }

    }
    fun getItemsFromApi() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = apiRepository.getFiles()
            if(response.isSuccessful) {
                executeItemsResponseBody(response)
                Log.d("networkList","initial Response succesfull")
            }
        }
        catch (e:HttpException){
            Log.d("networkList","NetworkListException")
        }
    }

    private fun handleResponse(response: Response<FilesInfo>): ResponseType<FilesInfo> {
        if(response.isSuccessful){
            return ResponseType.Success(response.body())
        }
        val obj = JSONObject(response.body().toString())
        val errorMessage = obj.getString("message")
        return ResponseType.Error(message = errorMessage)
    }

}