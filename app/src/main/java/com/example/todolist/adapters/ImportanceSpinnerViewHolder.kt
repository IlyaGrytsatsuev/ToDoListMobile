package com.example.todolist.adapters

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.utils.Importance

class ImportanceSpinnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val spinner : Spinner = itemView.findViewById(R.id.importance_spinner)
    val importanceList = listOf<String>(
        Importance.NONE.text,
        Importance.LOW.text, Importance.HIGH.text)
    fun onBind(item: ToDoItemEntity, context: Context){
        var arrayAdapter = ImportanceSpinnerAdapter(context, importanceList)
        spinner.adapter = arrayAdapter
        spinner.setSelection(
            when (item.importance) {
                Importance.LOW.text -> 1
                Importance.HIGH.text -> 2
                else -> 0
            })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                item.importance = spinner.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
}