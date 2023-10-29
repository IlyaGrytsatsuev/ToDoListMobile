package com.example.todolist.presenter.adapters

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.domain.repository.DatabaseRepository
import com.example.todolist.R
import com.example.todolist.presenter.callbacks.EditToListCallback
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class DeleteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val deleteButton:Button = itemView.findViewById(R.id.delete_button)
    @Inject
    lateinit var repository: DatabaseRepository
    fun onBind(
        item: MutableStateFlow<ToDoItemEntity>, callback: EditToListCallback,
        deleteItem: (item: ToDoItemEntity) -> Unit
    ){
        if(item.value.text.isBlank()) {
            deleteButton.visibility = View.GONE
        }
        else {
            deleteButton.setOnClickListener {
                deleteItem(item.value)
                callback.onClick()
            }
        }
    }
}