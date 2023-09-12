package com.example.todolist

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ImportanceSpinnerAdapter(context: Context, itemsList:List<String>) : ArrayAdapter<String>(context,0, itemsList){

    //val title = "Важность"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.custom_spinner, parent, false)

        //val mainText = view.findViewById<TextView>(R.id.title)
        val subText = view.findViewById<TextView>(R.id.subtitle)

        val item = getItem(position)

        subText.text = item
        return view

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.custom_spinner_dropdown, parent, false)
        val text = view.findViewById<TextView>(R.id.dropdown_textview)
        text.text = if(getItem(position) == Importance.HIGH.text) "!! ${getItem(position)}"
        else getItem(position)
        if(getItem(position) == Importance.HIGH.text)
            text.setTextColor(Color.parseColor("#FF3B30"))
        return view
    }
}