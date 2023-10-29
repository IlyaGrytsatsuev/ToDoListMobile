package com.example.todolist.presenter.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface EditRecyclerDelegate {
    fun matchesDelegate(item: EditRecyclerDelegate):Boolean
    fun getViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: MutableStateFlow<ToDoItemEntity>)
}