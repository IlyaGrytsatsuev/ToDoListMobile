package com.example.todolist.adapters

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.db.ToDoItemEntity
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.Calendar

class UntilDateViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {
    var dateText:TextView = itemView.findViewById(R.id.date)
    var toggle: SwitchMaterial = itemView.findViewById(R.id.toggle)
    val calendar: Calendar = Calendar.getInstance()
    lateinit var datePicker: DatePickerDialog


    fun onBind(item: MutableLiveData<ToDoItemEntity>){
        if(item.value?.untilDate != null) {
                    toggle.isChecked = true
                    calendar.time = item.value?.untilDate
                    var day = calendar.get(Calendar.DAY_OF_MONTH)
                    var monthString = getMonthString(calendar.get(Calendar.MONTH))
                    dateText.text = "$day $monthString"
        }
        var tmp = item.value
        val datePickerListenerFunc =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                dateText.text = "$dayOfMonth ${getMonthString(month)}"
                dateText.visibility = View.VISIBLE
                tmp?.untilDate = calendar.time
                item.postValue(tmp)
            }

        toggle.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked && item.value?.untilDate == null) {
                    //Log.d("text_copy", item.text)
                    datePicker = DatePickerDialog(
                        context,
                        datePickerListenerFunc,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    datePicker.show()
                }
                else
                    datePicker.hide()
            }
    }

    private fun getMonthString(monthNumber: Int): String {
        val russianMonths = context.resources.getStringArray(R.array.months)
        return russianMonths[monthNumber]
    }

}