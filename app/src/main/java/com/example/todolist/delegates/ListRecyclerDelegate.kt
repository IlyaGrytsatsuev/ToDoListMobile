package com.example.todolist.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.callbacks.EditToListCallback
import com.example.todolist.callbacks.RecyclerOnClickCallBack
import com.example.todolist.db.ToDoItemEntity

interface ListRecyclerDelegate {
    fun matchesDelegate(item: ToDoItemEntity):Boolean
    fun getViewHolder(parent:ViewGroup, callBack: RecyclerOnClickCallBack) : RecyclerView.ViewHolder
    fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: ToDoItemEntity)



}