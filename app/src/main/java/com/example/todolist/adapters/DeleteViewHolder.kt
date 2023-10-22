package com.example.todolist.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.callbacks.EditToListCallback
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.repository.ToDoItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class DeleteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val deleteButton:Button = itemView.findViewById(R.id.delete_button)
    @Inject
    lateinit var repository: ToDoItemsRepository
    fun onBind(
        item: MutableStateFlow<ToDoItemEntity>, callback: EditToListCallback,
        deleteItem: (item: ToDoItemEntity) -> Unit,
        context: Context
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