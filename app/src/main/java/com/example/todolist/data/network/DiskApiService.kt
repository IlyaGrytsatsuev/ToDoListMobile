package com.example.todolist.data.network

import com.example.todolist.data.network.responseModels.FilesListResponse
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.utils.Constants
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

interface DiskApiService {
    @GET("resources")
    suspend fun getFilesList(
        @Query("path")
        path:String = Constants.APP_PATH,
        @Query("sort")
        sort:String = "created"): Response<FilesListResponse>

    @PUT("resources")
    suspend fun createApplicationFolder(
        @Query("path")
        path: String = Constants.APP_PATH): Response<ResponseBody>
//
//    @GET("resources/upload")
//    suspend fun getUploadUrl(@Query("path")
//                             path: String,
//                             @Query("overwrite")
//                             overwrite:Boolean = false): Response<UploadLink>

//    @Headers("require_OAuth: false")
//    @Multipart
//    @PUT
//    suspend fun uploadFile(@Url url:String,
//                           @Part jsonFile: MultipartBody.Part): Response<CreatedResponse>
//
//    @DELETE("resources")
//    suspend fun deleteFile(@Query("path") path: String,
//                           @Query("permanently")
//                           permanently:Boolean = true):Response<ResponseBody>
//
//    @GET
//    suspend fun downloadFile(@Url downloadUrl:String):Response<ToDoItemEntity>

}