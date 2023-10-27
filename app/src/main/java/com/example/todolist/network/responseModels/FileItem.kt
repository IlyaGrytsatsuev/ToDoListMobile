package com.example.todolist.network.responseModels

import com.google.gson.annotations.SerializedName

data class FileItem(
    @SerializedName("name")
    val name : String)
