package com.example.todolist.presenter.delegates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.R
import com.example.todolist.presenter.adapters.TextEditViewHolder
import kotlinx.coroutines.flow.MutableStateFlow

class TextEditDelegate(
    val context: Context,
    val saveButton: Button,
) : EditRecyclerDelegate {
    override fun matchesDelegate(item: EditRecyclerDelegate): Boolean {
        return item is TextEditDelegate
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return TextEditViewHolder(
            layoutInflater.inflate(
                R.layout.input_field_layout,
                parent,
                false
            ))
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: MutableStateFlow<ToDoItemEntity>) {
        (viewHolder as TextEditViewHolder).let { viewHolder.onBind(item, context, saveButton) }
    }
}