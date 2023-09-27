package com.example.todolist.delegates

import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.db.ToDoItemEntity

interface EditRecyclerDelegate {
    fun matchesDelegate(item: EditRecyclerDelegate):Boolean
    fun getViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: MutableLiveData<ToDoItemEntity>)
}