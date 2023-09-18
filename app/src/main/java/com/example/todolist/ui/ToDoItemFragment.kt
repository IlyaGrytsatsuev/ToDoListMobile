package com.example.todolist.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.utils.Importance
import com.example.todolist.R
import com.example.todolist.adapters.ImportanceSpinnerAdapter
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.viewModel.DatabaseViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ToDoItemFragment : Fragment() {
    private val activityViewModel: DatabaseViewModel by viewModels()
    lateinit var navController: NavController
    lateinit var exitButton:ImageButton
    lateinit var saveButton: Button
    lateinit var textEdit: TextInputEditText
    lateinit var spinner: Spinner
    lateinit var adapter: ArrayAdapter<String>
    lateinit var toggle: SwitchMaterial
    lateinit var dateText: TextView
    lateinit var deleteButton: Button
    var item: ToDoItemEntity = ToDoItemEntity()
    val calendar: Calendar = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.N)
    lateinit var datePicker: DatePickerDialog

    val importanceList = listOf<String>(
        Importance.NONE.text,
        Importance.LOW.text, Importance.HIGH.text)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_to_do_item, container, false)
        navController = findNavController()
        spinner = view.findViewById(R.id.importance_spinner)
        adapter = ImportanceSpinnerAdapter(requireContext(), importanceList)
        spinner.adapter = adapter
        toggle = view.findViewById(R.id.toggle)
        dateText = view.findViewById(R.id.date)
        exitButton = view.findViewById(R.id.close_button)

        exitButton.setOnClickListener{
            navController.popBackStack()
        }

        deleteButton = view.findViewById(R.id.delete_button)
        deleteButton.setOnClickListener {
            activityViewModel.deleteItem(item)
            navController.popBackStack()
        }

        saveButton = view.findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            //item.text = textEdit.text.toString()
            item.text = textEdit.text.toString()
            item.importance = spinner.selectedItem.toString()
            activityViewModel.addToDoItem(item)
            navController.popBackStack()
        }

        val args: ToDoItemFragmentArgs by navArgs()
        var item_id :Int? = args.itemId
        textEdit = view.findViewById(R.id.to_do_input)
        if(item_id != null) {
            if (item_id > -1){
                activityViewModel.getItemById(item_id)
                activityViewModel.currentItem.observe(viewLifecycleOwner){
                    item = it
                    textEdit.setText(it.text)
                    if(it.untilDate != null) {
                        toggle.isChecked = true
                        calendar.time = it.untilDate
                        var day = calendar.get(Calendar.DAY_OF_MONTH)
                        var monthString = getMonthString(calendar.get(Calendar.MONTH))
                        dateText.text = "$day $monthString"
                    }
                    spinner.setSelection(
                        when (item.importance) {
                            Importance.LOW.text -> 1
                            Importance.HIGH.text -> 2
                            else -> 0
                        })
                }
            }
            var entity = ToDoItemEntity()
            toggle.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked && item.untilDate == null) {
                    //Log.d("text_copy", item.text)
                    datePicker = DatePickerDialog(
                        requireContext(),
                        datePickerListenerFunc,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    datePicker.show()
                }
            }
        }
        return view
    }

    val datePickerListenerFunc =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            dateText.text = "$dayOfMonth ${getMonthString(month)}"
            dateText.visibility = View.VISIBLE
            item.untilDate = calendar.time
    }

    fun getMonthString(monthNumber: Int): String {
        val russianMonths = resources.getStringArray(R.array.months)
        return russianMonths[monthNumber]
    }


}