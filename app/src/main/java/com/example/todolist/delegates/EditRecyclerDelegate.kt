package com.example.todolist.delegates

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.callbacks.EditToListCallback
import com.example.todolist.callbacks.RecyclerOnClickCallBack
import com.example.todolist.db.ToDoItemEntity

interface EditRecyclerDelegate {
    fun matchesDelegate(item: EditRecyclerDelegate):Boolean
    fun getViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: ToDoItemEntity)
}