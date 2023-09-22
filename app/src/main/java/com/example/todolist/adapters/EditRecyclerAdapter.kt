package com.example.todolist.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.delegates.EditRecyclerDelegate
import com.example.todolist.db.ToDoItemEntity

class EditRecyclerAdapter(var item :ToDoItemEntity, val delegates:List<EditRecyclerDelegate>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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

//    private val differCallback = object : DiffUtil.ItemCallback<ToDoItemEntity>() {
//        override fun areItemsTheSame(oldItem: ToDoItemEntity, newItem: ToDoItemEntity): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: ToDoItemEntity, newItem: ToDoItemEntity): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)



}



