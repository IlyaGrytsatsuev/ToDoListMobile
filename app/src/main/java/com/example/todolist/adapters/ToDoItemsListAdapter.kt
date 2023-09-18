package com.example.todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.callbacks.RecyclerOnClickCallBack
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.repository.ToDoItemsRepository
import com.example.todolist.R
import javax.inject.Inject

class ToDoItemsListAdapter(var callBack: RecyclerOnClickCallBack) : RecyclerView.Adapter<ToDoItemViewHolder>() {

    @Inject
    lateinit var toDoItemsRepository: ToDoItemsRepository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ToDoItemViewHolder(
            layoutInflater.inflate(
                R.layout.to_do_item,
                parent,
                false
            ), callBack )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }

    private val differCallback = object : DiffUtil.ItemCallback<ToDoItemEntity>(){
        override fun areItemsTheSame(oldItem: ToDoItemEntity, newItem: ToDoItemEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoItemEntity, newItem: ToDoItemEntity): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

}