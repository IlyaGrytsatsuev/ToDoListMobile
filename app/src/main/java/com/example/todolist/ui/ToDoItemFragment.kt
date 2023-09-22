package com.example.todolist.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.delegates.EditRecyclerDelegate
import com.example.todolist.R
import com.example.todolist.adapters.EditRecyclerAdapter
import com.example.todolist.callbacks.EditToListCallback
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.delegates.DeleteDelegate
import com.example.todolist.delegates.ImportanceSpinnerDelegate
import com.example.todolist.delegates.TextEditDelegate
import com.example.todolist.delegates.UntilDateDelegate
import com.example.todolist.viewModel.DatabaseViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class ToDoItemFragment : Fragment() {
    private val viewModel: DatabaseViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: EditRecyclerAdapter
    lateinit var navController: NavController
    lateinit var exitButton:ImageButton
    lateinit var saveButton: Button
    lateinit var deleteFun: (item:ToDoItemEntity) -> Unit
    var item: ToDoItemEntity = ToDoItemEntity()
    var updated = false
    lateinit var delegates : List<EditRecyclerDelegate>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_to_do_item, container, false)
        navController = findNavController()
        deleteFun = {
            viewModel.deleteItem(item)
        }
        delegates = listOf(TextEditDelegate(requireContext()),
            ImportanceSpinnerDelegate(requireContext()) ,
            UntilDateDelegate(requireContext()),
            DeleteDelegate(requireContext(),
                EditToListCallback(navController),
                deleteFun))
        recyclerView = view.findViewById(R.id.to_do_item_recycler)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)

        val args: ToDoItemFragmentArgs by navArgs()
        var item_id :Int? = args.itemId
        if(item_id != null) {
            if (item_id > -1){
                viewModel.getItemById(item_id)
                viewModel.currentItem.observe(viewLifecycleOwner){
                    updated = true
                    item = it
                    initializeRecycler(view)
                    Log.d("text", item.text)
                }
            }
        }
        if(!updated) {
            initializeRecycler(view)
        }

        return view
    }

    fun initializeRecycler(view:View){
        adapter = EditRecyclerAdapter(item, delegates)
        recyclerView.adapter = adapter
        exitButton = view.findViewById(R.id.close_button_collapsed)
        exitButton.setOnClickListener {
            navController.popBackStack()
        }
        saveButton = view.findViewById(R.id.save_button_collapsed)
        saveButton.setOnClickListener {
            Log.d("item", item.text)
            Log.d("importance", item.importance)
            viewModel.addToDoItem(item)
            navController.popBackStack()
        }
    }

}