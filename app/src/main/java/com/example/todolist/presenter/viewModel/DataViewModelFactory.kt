package com.example.todolist.presenter.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.domain.usecases.AddToDoItem
import com.example.todolist.domain.usecases.CreateAppPackageInDisk
import com.example.todolist.domain.usecases.DeleteToDoItemFromDB
import com.example.todolist.domain.usecases.GetItemsListFromApi
import com.example.todolist.domain.usecases.GetOAuthToken
import com.example.todolist.domain.usecases.GetToDoItemByIdFromDB
import com.example.todolist.domain.usecases.GetToDoItemsFromDB
import com.example.todolist.domain.usecases.SaveOAuthToken
import javax.inject.Inject

class DataViewModelFactory @Inject constructor(
    private val getToDoItemFromDB: GetToDoItemsFromDB,
    private val getToDoItemByIdFromDB: GetToDoItemByIdFromDB,
    private val addToDoItemFromDB: AddToDoItem,
    private val deleteToDoItemFromDB: DeleteToDoItemFromDB,
    private val getOAuthToken: GetOAuthToken,
    private val saveOauthToken: SaveOAuthToken,
    private val deleteOAuthToken: GetOAuthToken,
    private val getItemsListFromApi: GetItemsListFromApi,
    private val createAppPackageInDisk: CreateAppPackageInDisk
)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DataViewModel(
            getToDoItemFromDB, getToDoItemByIdFromDB,
            addToDoItemFromDB, deleteToDoItemFromDB,
            getOAuthToken, saveOauthToken,
            deleteOAuthToken, getItemsListFromApi,
            createAppPackageInDisk) as T
    }
}