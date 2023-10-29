package com.example.todolist.presenter.delegates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.R
import com.example.todolist.presenter.adapters.UntilDateViewHolder
import kotlinx.coroutines.flow.MutableStateFlow

class UntilDateDelegate(var context: Context) : EditRecyclerDelegate {
    override fun matchesDelegate(item: EditRecyclerDelegate): Boolean {
        return item is UntilDateDelegate
    }

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return UntilDateViewHolder(
            layoutInflater.inflate(
                R.layout.until_date_layout,
                parent,
                false
            ), context)
    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: MutableStateFlow<ToDoItemEntity>) {
        (viewHolder as UntilDateViewHolder).let{viewHolder.onBind(item)}
    }
}