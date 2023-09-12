package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var navController: NavController
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
        recyclerView.adapter = ToDoItemsListAdapter(RecyclerToEditCallback(navController))


        return view
    }

}