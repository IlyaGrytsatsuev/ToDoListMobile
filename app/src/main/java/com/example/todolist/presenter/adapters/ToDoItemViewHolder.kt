package com.example.todolist.presenter.adapters

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.R
import com.example.todolist.presenter.callbacks.RecyclerOnClickCallBack


class ToDoItemViewHolder(var itemView: View,
                         var callBack: RecyclerOnClickCallBack
) :
    RecyclerView.ViewHolder(itemView) {

    var completeIcon : AppCompatImageView
        = itemView.findViewById(R.id.complete_state)
    private var itemText : TextView
    = itemView.findViewById(R.id.item_text)


    fun onBind(toDoItem: ToDoItemEntity, context:Context) {

        itemView.setOnClickListener {
            callBack.onClick(toDoItem.id)
        }

        if (toDoItem.isComplete)
            completeIcon.setImageResource(R.drawable.done_recycler_icon)

        itemText.text = toDoItem.text
        if (toDoItem.isComplete) {
            itemText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemText.setTextColor(ContextCompat.getColor(context, R.color.grey))
        }
    }


}