package com.example.todolist.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapters.ToDoItemViewHolder
import com.example.todolist.callbacks.EditToListCallback
import com.example.todolist.callbacks.RecyclerOnClickCallBack
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.utils.Importance

class ImportantDelegate : ListRecyclerDelegate {
    override fun matchesDelegate(item: ToDoItemEntity) =
        item.importance == Importance.HIGH.text

    override fun getViewHolder(
        parent: ViewGroup,
        callBack: RecyclerOnClickCallBack
    ): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ToDoItemViewHolder(
            layoutInflater.inflate(
                R.layout.important_to_do_item,
                parent,
                false
            ), callBack )    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: ToDoItemEntity) {
        (viewHolder as ToDoItemViewHolder).let { viewHolder.onBind(item) }
    }

}