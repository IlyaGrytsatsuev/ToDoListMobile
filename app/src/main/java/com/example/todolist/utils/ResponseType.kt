package com.example.todolist.utils

sealed class ResponseType<T>(
    val data :T? = null,
    val message :String? = null
)
{
    class Processing<T>(data:T?) : ResponseType<T>(data)
    class Success<T>(data: T?):ResponseType<T>(data)
    class Error<T>(data: T? = null, message:String? = null):ResponseType<T>(data)



}
