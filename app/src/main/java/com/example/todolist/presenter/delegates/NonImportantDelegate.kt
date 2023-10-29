package com.example.todolist.presenter.delegates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.R
import com.example.todolist.presenter.adapters.ToDoItemViewHolder
import com.example.todolist.presenter.callbacks.RecyclerOnClickCallBack
import com.example.todolist.utils.Importance

class NonImportantDelegate(val context: Context) : ListRecyclerDelegate {
    override fun matchesDelegate(item: ToDoItemEntity)
    = item.importance != Importance.HIGH.text

    override fun getViewHolder(
        parent: ViewGroup,
        callBack: RecyclerOnClickCallBack
    ): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ToDoItemViewHolder(
            layoutInflater.inflate(
                R.layout.to_do_item,
                parent,
                false
            ), callBack )    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: ToDoItemEntity) {
        (viewHolder as ToDoItemViewHolder).let { viewHolder.onBind(item, context) }
    }
}