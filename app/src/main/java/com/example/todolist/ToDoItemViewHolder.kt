package com.example.todolist

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView

class ToDoItemViewHolder(var itemView: View, var callBack: RecyclerOnClickCallBack) :
    RecyclerView.ViewHolder(itemView) {

    var completeIcon : AppCompatImageView
        = itemView.findViewById(R.id.complete_state)
    var importantIcon : AppCompatImageView?
    = itemView.findViewById(R.id.important_image)
    var itemText : TextView
    = itemView.findViewById(R.id.item_text)

    fun onBind(toDoItem: ToDoItem){

        itemView.setOnClickListener{
            callBack.onClick(toDoItem.id)
        }

        var completeIconId = when{
            toDoItem.isComplete -> R.drawable.done_icon
            toDoItem.importance == Importance.HIGH.text -> R.drawable.important_undone_icon
            else -> R.drawable.undone_icon
        }
        completeIcon.setImageResource(completeIconId)

        if(toDoItem.importance == Importance.HIGH.text)
            importantIcon?.setImageResource(R.drawable.important_icon)

        itemText.text = toDoItem.text
    }


}