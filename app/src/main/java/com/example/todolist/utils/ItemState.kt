package com.example.todolist.utils

enum class ItemState(val text: String) {
    SYNCHRONIZED("synchronized"),
    ON_DELETE("on_delete"),
    ON_EDIT("on_edit"),
    ON_UPLOAD("on_upload")
}