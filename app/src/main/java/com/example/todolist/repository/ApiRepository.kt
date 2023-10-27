package com.example.todolist.repository

import com.example.todolist.network.services.DiskApiService
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor
    (private val apiService: DiskApiService) {

    suspend fun getFiles() = apiService.getFilesList()
    suspend fun getUploadUrl(path:String, overwrite:Boolean = false)
    = apiService.getUploadUrl(path, overwrite)

    suspend fun uploadFile(url: String, jsonFile: MultipartBody.Part) =
        apiService.uploadFile(url, jsonFile)

    suspend fun downloadFile(downloadUrl:String)
    = apiService.downloadFile(downloadUrl)

    suspend fun deleteFile(name:String) = apiService.deleteFile(name)
}