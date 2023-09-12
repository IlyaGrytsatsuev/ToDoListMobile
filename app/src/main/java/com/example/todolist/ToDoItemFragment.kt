package com.example.todolist

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class ToDoItemFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var exitButton:ImageButton
    lateinit var saveButton: Button
    lateinit var textEdit: TextInputEditText
    lateinit var spinner: Spinner
    lateinit var adapter: ArrayAdapter<String>
    lateinit var toggle: SwitchMaterial
    val importanceList = listOf<String>(Importance.NONE.text,
        Importance.LOW.text, Importance.HIGH.text)

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

        exitButton = view.findViewById(R.id.close_button)

        exitButton.setOnClickListener{
            navController.popBackStack()
        }

        saveButton = view.findViewById(R.id.save_button)

        val args: ToDoItemFragmentArgs by navArgs()
        var item_id :Int? = args.itemId
        if(item_id != null){
            textEdit = view.findViewById(R.id.to_do_input)
            var repository = ToDoItemsRepository.getInstance()
            var item = repository.getItemById(item_id)
            textEdit.setText(item?.text)
            if (item != null) {
                saveButton.setOnClickListener {
                    item.text = textEdit.text.toString()
                    navController.popBackStack()
                }
            }
        }


        return view
    }


}