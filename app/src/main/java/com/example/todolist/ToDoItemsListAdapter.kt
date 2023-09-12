package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ToDoItemsListAdapter(var callBack:RecyclerOnClickCallBack) : RecyclerView.Adapter<ToDoItemViewHolder>() {

    private val toDoList : MutableList<ToDoItem> = ToDoItemsRepository.getInstance().getItemsList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ToDoItemViewHolder(
            layoutInflater.inflate(
                R.layout.to_do_item,
                parent,
                false
            ), callBack )
    }

    override fun getItemCount() = toDoList.size

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        holder.onBind(toDoList[position])
    }

}