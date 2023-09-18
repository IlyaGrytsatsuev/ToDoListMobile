package com.example.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapters.ToDoItemsListAdapter
import com.example.todolist.callbacks.RecyclerToEditCallback
import com.example.todolist.viewModel.DatabaseViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment() {

     private val activityViewModel : DatabaseViewModel by activityViewModels()
    lateinit var recyclerView : RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var navController: NavController
    lateinit var adapter: ToDoItemsListAdapter
    lateinit var addButton:FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.to_do_items_list)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        recyclerView.layoutManager = layoutManager
        navController = findNavController()
        adapter = ToDoItemsListAdapter(RecyclerToEditCallback(navController))
        activityViewModel.getToDoItems()
        activityViewModel.itemsList.observe(viewLifecycleOwner){
            adapter.differ.submitList(it)
        }
        recyclerView.adapter = adapter

        addButton = view.findViewById(R.id.add_button)
        addButton.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_toDoItemFragment)
        }

        return view
    }

}