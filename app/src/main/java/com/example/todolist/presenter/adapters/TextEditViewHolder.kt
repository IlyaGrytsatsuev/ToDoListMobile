package com.example.todolist.presenter.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.MutableStateFlow

class TextEditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textEdit: TextInputEditText = itemView.findViewById(R.id.to_do_input)

    fun onBind(
        item: MutableStateFlow<ToDoItemEntity>,
        context: Context,
        saveButton: Button,
    ) {
        textEdit.setText(item.value.text)
        saveButton.setTextColor(ContextCompat.getColor(context, R.color.grey))
        saveButton.isClickable = false
        //var oldText = item.value.text
        textEdit.addTextChangedListener {
            if (it.isNullOrBlank()) {
                saveButton.setTextColor(ContextCompat.getColor(context, R.color.grey))
                saveButton.isClickable = false
            } else {
                var tmp = item.value.copy()
                tmp.text = it.toString().trim()
                item.value = tmp

                Log.d("equalityChanged", "textChanged = ${item.value.text}")
            }
        }

    }
}