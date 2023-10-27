package com.example.todolist.network.responseModels

data class Item(
    val name: String,
    val modified: String,
    val sizes: List<Size>
)