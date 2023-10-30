package com.example.todolist.data.network.responseModels

data class Item(
    val name: String,
    val modified: String,
    val sizes: List<Size>
)