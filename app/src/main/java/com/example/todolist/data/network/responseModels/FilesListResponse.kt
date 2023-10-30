package com.example.todolist.data.network.responseModels

import com.google.gson.annotations.SerializedName

data class FilesListResponse (
    @SerializedName("_embedded")
    val embedded:Embedded
)