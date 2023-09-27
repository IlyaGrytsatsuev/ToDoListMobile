package com.example.todolist.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.delegates.ImportantDelegate
import com.example.todolist.delegates.ListRecyclerDelegate
import com.example.todolist.delegates.NonImportantDelegate
import com.example.todolist.R
import com.example.todolist.adapters.ToDoItemsListAdapter
import com.example.todolist.callbacks.RecyclerToEditCallback
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.utils.SwipeGesture
import com.example.todolist.viewModel.DatabaseViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment() {

    private val activityViewModel: DatabaseViewModel by activityViewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var navController: NavController
    lateinit var adapter: ToDoItemsListAdapter
    lateinit var addButton: FloatingActionButton
    lateinit var setCompleteFun: (position: Int) -> Unit
    lateinit var deleteFun: (position: Int) -> Unit
    lateinit var swipeGesture: SwipeGesture
    lateinit var isCompleteCheck: (position: Int) -> Boolean



    lateinit var  adapterDelegates :List<ListRecyclerDelegate>

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list, container, false)
        adapterDelegates = listOf(NonImportantDelegate(requireContext()),
            ImportantDelegate(requireContext()))
        recyclerView = view.findViewById(R.id.to_do_items_list)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        recyclerView.layoutManager = layoutManager
        navController = findNavController()
        adapter = ToDoItemsListAdapter(RecyclerToEditCallback(navController), adapterDelegates)
        activityViewModel.getToDoItems()
        activityViewModel.itemsList.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it)
        }
        recyclerView.adapter = adapter

        addButton = view.findViewById(R.id.add_button)
        addButton.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_toDoItemFragment)
        }


        setCompleteFun = { position ->
            val item = adapter.differ.currentList[position]
            Log.d("Item_text", item.text)
            if(!item.isComplete){
                item.isComplete = true
                activityViewModel.addToDoItem(item)
            }
            adapter.notifyItemChanged(position)
        }
        deleteFun = { position ->
            val item = adapter.differ.currentList[position]
            Log.d("Item_text", item.text)
            activityViewModel.deleteItem(item)
            showDeleteSnackBar(view, item)
        }

        isCompleteCheck = { position ->
            val item = adapter.differ.currentList[position]
            item.isComplete
        }


        swipeGesture = SwipeGesture(requireContext())
        swipeGesture.rightSwipeListener = setCompleteFun
        swipeGesture.leftSwipeListener = deleteFun
        swipeGesture.isCompleteListener = isCompleteCheck

        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        return view
    }

    private fun showDeleteSnackBar(view: View, item:ToDoItemEntity){
        val snackbar = Snackbar.make(view,
            requireContext().getString(R.string.delete_snackbar),
            Snackbar.LENGTH_LONG)
            .apply {
                setAction(requireContext().getString(R.string.cancel)){
                    activityViewModel.addToDoItem(item)
                }
                setActionTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            }
        val snackbarView = snackbar.view
        val snackbarTextView : TextView
        = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text)
        snackbarTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        snackbarView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.show()
    }


}

