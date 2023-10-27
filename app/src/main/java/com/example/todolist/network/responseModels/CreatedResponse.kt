package com.example.todolist.network.responseModels

import com.google.gson.annotations.SerializedName

data class CreatedResponse (
    @SerializedName("Content_Length")
    val contentLength: Int)