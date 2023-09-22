package com.example.todolist.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.db.ToDoItemEntity
import com.google.android.material.textfield.TextInputEditText

class TextEditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textEdit: TextInputEditText = itemView.findViewById(R.id.to_do_input)

    fun onBind(item:ToDoItemEntity){
        textEdit.setText(item.text)
        textEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                item.text = textEdit.text.toString()
            }
        })
    }
}