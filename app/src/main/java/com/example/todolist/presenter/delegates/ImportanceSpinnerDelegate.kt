package com.example.todolist.presenter.delegates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.R
import com.example.todolist.presenter.adapters.ImportanceSpinnerViewHolder
import kotlinx.coroutines.flow.MutableStateFlow

class ImportanceSpinnerDelegate(var context: Context) : EditRecyclerDelegate {

    override fun matchesDelegate(item: EditRecyclerDelegate): Boolean {
        return item is ImportanceSpinnerDelegate
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ImportanceSpinnerViewHolder(
            layoutInflater.inflate(
                R.layout.spinner_layout,
                parent,
                false
            ))
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: MutableStateFlow<ToDoItemEntity>) {
        (viewHolder as ImportanceSpinnerViewHolder).let { viewHolder.onBind(item, context) }
    }
}