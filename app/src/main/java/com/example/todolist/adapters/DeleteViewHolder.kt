package com.example.todolist.adapters

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.callbacks.EditToListCallback
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.repository.ToDoItemsRepository
import javax.inject.Inject

class DeleteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val deleteButton:Button = itemView.findViewById(R.id.delete_button)
    @Inject
    lateinit var repository: ToDoItemsRepository
    fun onBind(item:ToDoItemEntity, callback: EditToListCallback,
               deleteItem:(item:ToDoItemEntity)->Unit){
        deleteButton.setOnClickListener {
            deleteItem(item)
            callback.onClick()
       }
    }
}