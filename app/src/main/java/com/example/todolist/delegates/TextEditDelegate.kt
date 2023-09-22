package com.example.todolist.delegates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapters.TextEditViewHolder
import com.example.todolist.db.ToDoItemEntity

class TextEditDelegate(context: Context?) : EditRecyclerDelegate {
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

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: ToDoItemEntity) {
        (viewHolder as TextEditViewHolder).let { viewHolder.onBind(item) }
    }
}