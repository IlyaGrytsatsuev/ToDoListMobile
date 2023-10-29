package com.example.todolist.presenter.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.presenter.callbacks.RecyclerOnClickCallBack

interface ListRecyclerDelegate {
    fun matchesDelegate(item: ToDoItemEntity):Boolean
    fun getViewHolder(parent:ViewGroup, callBack: RecyclerOnClickCallBack) : RecyclerView.ViewHolder
    fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: ToDoItemEntity)



}