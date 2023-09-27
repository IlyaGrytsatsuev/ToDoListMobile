package com.example.todolist.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.db.ToDoItemEntity
import com.google.android.material.textfield.TextInputEditText

class TextEditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textEdit: TextInputEditText = itemView.findViewById(R.id.to_do_input)

    fun onBind(
        item: MutableLiveData<ToDoItemEntity>,
        context: Context,
        saveButton: Button,
    ){
        textEdit.setText(item.value?.text)
        saveButton.setTextColor(ContextCompat.getColor(context, R.color.grey))
        saveButton.isClickable = false
        var oldText = item.value?.text
        textEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrBlank()){
                    saveButton.setTextColor(ContextCompat.getColor(context, R.color.grey))
                    saveButton.isClickable = false
                }
                else {
                    var tmp = item.value
                    tmp?.text = textEdit.text.toString().trim()
                    item.postValue(tmp)
//                    when {
//                        item.value?.text == oldText -> {
//                            saveButton.setTextColor(ContextCompat.getColor(context, R.color.grey))
//                            saveButton.isClickable = false
//                        }
//                        else -> {
//                            saveButton.setTextColor(ContextCompat.getColor(context, R.color.blue))
//                            saveButton.isClickable = true
//                        }
//                    }
                }
            }
        })
    }
}