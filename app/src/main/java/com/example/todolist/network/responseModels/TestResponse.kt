package com.example.todolist.network.responseModels

import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName("_embedded")
    val embedded: Embedded,
)