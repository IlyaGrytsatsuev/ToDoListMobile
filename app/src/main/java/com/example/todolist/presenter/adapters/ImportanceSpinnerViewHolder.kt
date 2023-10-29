package com.example.todolist.presenter.adapters

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.R
import com.example.todolist.utils.Importance
import kotlinx.coroutines.flow.MutableStateFlow

class ImportanceSpinnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val spinner : Spinner = itemView.findViewById(R.id.importance_spinner)
    val importanceList = listOf<String>(
        Importance.NONE.text,
        Importance.LOW.text, Importance.HIGH.text)
    fun onBind(
        item: MutableStateFlow<ToDoItemEntity>,
        context: Context,
    ){
        var arrayAdapter = ImportanceSpinnerAdapter(context, importanceList)
        spinner.adapter = arrayAdapter
        var tmp = item.value.copy()
        spinner.setSelection(
            when (tmp.importance) {
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
                //val tmp = item.value
                tmp.importance = spinner.selectedItem.toString()
                item.value = tmp.copy()
                Log.d("equality", "selectedItem")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
}