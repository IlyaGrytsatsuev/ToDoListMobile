package com.example.todolist.adapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.callbacks.RecyclerOnClickCallBack
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.utils.Importance

class ToDoItemViewHolder(var itemView: View, var callBack: RecyclerOnClickCallBack) :
    RecyclerView.ViewHolder(itemView) {

    var completeIcon : AppCompatImageView
        = itemView.findViewById(R.id.complete_state)
    var importantIcon : AppCompatImageView?
    = itemView.findViewById(R.id.important_image)
    var itemText : TextView
    = itemView.findViewById(R.id.item_text)

    fun onBind(toDoItem: ToDoItemEntity){

        itemView.setOnClickListener{
            callBack.onClick(toDoItem.id)
        }

        if(toDoItem.isComplete)
            completeIcon.setImageResource(R.drawable.done_icon)

        itemText.text = toDoItem.text
    }


}