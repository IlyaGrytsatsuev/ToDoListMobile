package com.example.todolist.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
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
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoItemFragment : Fragment() {
    private val viewModel: DatabaseViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: EditRecyclerAdapter
    lateinit var navController: NavController
    lateinit var exitButton:ImageButton
    lateinit var saveButton: Button
    lateinit var deleteFun: (item:ToDoItemEntity) -> Unit
//    lateinit var olditem : ToDoItemEntity


    var item: MutableLiveData<ToDoItemEntity> = MutableLiveData<ToDoItemEntity>()
    var updated = false
    lateinit var delegates : List<EditRecyclerDelegate>

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_to_do_item, container, false)

        navController = findNavController()
        deleteFun = {
            item.value?.let { it1 -> viewModel.deleteItem(it1) }
        }

        recyclerView = view.findViewById(R.id.to_do_item_recycler)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)

        val appBar: AppBarLayout = view.findViewById(R.id.appBar)
        appBar.elevation = 0f
        recyclerView.setOnScrollChangeListener { _,_,scrollY,_,oldScrollY ->
            val scrollDelta = scrollY - oldScrollY
            if(scrollDelta > 0 )
                appBar.elevation = 9f
            else
                appBar.elevation = 0f
        }

        val args: ToDoItemFragmentArgs by navArgs()
        var item_id :Int? = args.itemId
        if(item_id != null) {
            if (item_id > -1){
                viewModel.getItemById(item_id)
                updated = true
                viewModel.currentItem.observe(viewLifecycleOwner){
                    item.postValue(it)
                    initializeRecycler(view)
                    Log.d("text", viewModel.oldItem.text)
                }
            }
        }
        if(!updated) {
            viewModel.oldItem = ToDoItemEntity()
            item.postValue(ToDoItemEntity())
            initializeRecycler(view)
        }


        return view
    }

    fun initializeRecycler(view:View){
        var curItem = ToDoItemEntity()
        saveButton = view.findViewById(R.id.save_button_collapsed)
        delegates = listOf(TextEditDelegate(requireContext(), saveButton),
            ImportanceSpinnerDelegate(requireContext()) ,
            UntilDateDelegate(requireContext()),
            DeleteDelegate(requireContext(),
                EditToListCallback(navController),
                deleteFun))
        if(!updated)
            checkEquality(curItem)
        item.observe(viewLifecycleOwner){
            curItem = it
            checkEquality(curItem)
        }
        adapter = EditRecyclerAdapter(item, delegates)
        recyclerView.adapter = adapter
        exitButton = view.findViewById(R.id.close_button_collapsed)
        exitButton.setOnClickListener {
            navController.popBackStack()
        }
        saveButton.setOnClickListener {
            //Log.d("item", item.text)
            //Log.d("importance", item.importance)
            curItem.let { it1 -> viewModel.addToDoItem(it1) }
            navController.popBackStack()
        }

    }

    fun checkEquality(curItem:ToDoItemEntity){
        if(curItem == viewModel.oldItem){
            Log.d("equality", "true")
            saveButton.isClickable = false
            saveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        }
        else {
            Log.d("equality", "false")
            saveButton.isClickable = true
            saveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        }
    }


}