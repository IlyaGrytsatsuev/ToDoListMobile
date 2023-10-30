package com.example.todolist.presenter.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.presenter.callbacks.RecyclerOnClickCallBack
import com.example.todolist.presenter.delegates.ListRecyclerDelegate
import javax.inject.Inject

class ToDoItemsListAdapter (
    private var callBack: RecyclerOnClickCallBack,
    private val delegates : List<ListRecyclerDelegate>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].getViewHolder(parent, callBack)
    }

    override fun getItemViewType(position: Int)
    = delegates.indexOfFirst { delegate -> delegate.matchesDelegate(differ.currentList[position]) }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)].bindViewHolder(holder, differ.currentList[position])
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