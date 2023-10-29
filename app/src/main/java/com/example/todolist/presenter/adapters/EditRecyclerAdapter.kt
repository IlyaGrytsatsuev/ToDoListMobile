package com.example.todolist.presenter.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.presenter.delegates.EditRecyclerDelegate
import kotlinx.coroutines.flow.MutableStateFlow

class EditRecyclerAdapter(var item : MutableStateFlow<ToDoItemEntity>, val delegates:List<EditRecyclerDelegate>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].getViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun getItemCount(): Int {
        return delegates.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)]
            .bindViewHolder(holder, item)
    }



}



