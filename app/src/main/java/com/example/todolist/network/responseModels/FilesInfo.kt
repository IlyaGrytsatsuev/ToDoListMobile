package com.example.todolist.network.responseModels

import com.google.gson.annotations.SerializedName

data class FilesInfo (
    @SerializedName("items")
    val filesList : List<FileItem> = listOf()
)

