package com.example.todolist.adapters

import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.delegates.EditRecyclerDelegate
import com.example.todolist.db.ToDoItemEntity
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



