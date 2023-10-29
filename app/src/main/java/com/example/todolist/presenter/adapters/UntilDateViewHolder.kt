package com.example.todolist.presenter.adapters

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.R
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar

class UntilDateViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {
    var dateText:TextView = itemView.findViewById(R.id.date)
    var toggle: SwitchMaterial = itemView.findViewById(R.id.toggle)
    val calendar: Calendar = Calendar.getInstance()
    var datePicker: DatePickerDialog? = null


    fun onBind(item: MutableStateFlow<ToDoItemEntity>){
        var tmp = item.value.copy()
        if(item.value.untilDate != null) {
                    toggle.isChecked = true
                    calendar.time = tmp.untilDate
                    var day = calendar.get(Calendar.DAY_OF_MONTH)
                    var monthString = getMonthString(calendar.get(Calendar.MONTH))
                    dateText.text = "$day $monthString"
        }

        val datePickerListenerFunc =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                dateText.text = "$dayOfMonth ${getMonthString(month)}"
                dateText.visibility = View.VISIBLE
                tmp.untilDate = calendar.time
                item.value = tmp.copy()
            }


        toggle.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked ) {
                    //Log.d("text_copy", item.text)
                    datePicker = DatePickerDialog(
                        context,
                        datePickerListenerFunc,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    datePicker?.setOnCancelListener {
                        toggle.isChecked = false
                    }
                    datePicker?.show()
                }
                else {
                    tmp.untilDate = null
                    item.value = tmp.copy()
                    datePicker?.hide()
                    dateText.visibility = View.INVISIBLE
                }
            }
    }

    private fun getMonthString(monthNumber: Int): String {
        val russianMonths = context.resources.getStringArray(R.array.months)
        return russianMonths[monthNumber]
    }

}