package com.example.todolist.delegates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapters.DeleteViewHolder
import com.example.todolist.callbacks.EditToListCallback
import com.example.todolist.db.ToDoItemEntity

class DeleteDelegate(
    var context: Context,
    val callback: EditToListCallback,
    val deleteFun: (item: ToDoItemEntity) -> Unit
) : EditRecyclerDelegate {


    override fun matchesDelegate(item: EditRecyclerDelegate): Boolean {
        return item is DeleteDelegate
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return DeleteViewHolder(
            layoutInflater.inflate(
                R.layout.delete_button_layout,
                parent,
                false
            ))
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: ToDoItemEntity) {
        (viewHolder as DeleteViewHolder).let { viewHolder.onBind(item, callback, deleteFun) }
    }
}