package com.example.todolist.presenter.ui

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.presenter.delegates.EditRecyclerDelegate
import com.example.todolist.R
import com.example.todolist.presenter.adapters.EditRecyclerAdapter
import com.example.todolist.presenter.callbacks.EditToListCallback
import com.example.todolist.presenter.delegates.DeleteDelegate
import com.example.todolist.presenter.delegates.ImportanceSpinnerDelegate
import com.example.todolist.presenter.delegates.TextEditDelegate
import com.example.todolist.presenter.delegates.UntilDateDelegate
import com.example.todolist.presenter.viewModel.DataViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoItemFragment : Fragment() {
    private val viewModel: DataViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: EditRecyclerAdapter
    lateinit var navController: NavController
    lateinit var exitButton:ImageButton
    lateinit var saveButton: Button
    lateinit var deleteFun: (item:ToDoItemEntity) -> Unit


    var item: MutableStateFlow<ToDoItemEntity> = MutableStateFlow(ToDoItemEntity())
    private var updated = false
    lateinit var delegates : List<EditRecyclerDelegate>

    private lateinit var view: View

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_to_do_item, container, false)
        navController = findNavController()
        deleteFun = {
            viewModel.deleteItem(item.value)
        }
        saveButton = view.findViewById(R.id.save_button_collapsed)
        recyclerView = view.findViewById(R.id.to_do_item_recycler)
        recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )

        setUpAppBarElevationWhileScroll()
        initializeEditOrAddFragment()

        return view
    }

    private fun initializeEditOrAddFragment(){
        val args: ToDoItemFragmentArgs by navArgs()
        val item_id: Int = args.itemId
        if (item_id > -1) {
            viewModel.getItemById(item_id)
            updated = true

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.currentItem.collect {
                        item.value = it.copy()
                        initializeRecycler(view)
                        Log.d("equalityInitialOld", viewModel.oldItem.toString())
                    }
                }
            }
        }
        if (!updated) {
            saveButton.isClickable = false
            saveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            initializeRecycler(view)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpAppBarElevationWhileScroll(){
        val appBar: AppBarLayout = view.findViewById(R.id.appBar)
        appBar.elevation = 0f
        recyclerView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            val scrollDelta = scrollY - oldScrollY
            if (scrollDelta > 0)
                appBar.elevation = 9f
            else
                appBar.elevation = 0f
        }
    }

    private fun initializeDelegates(){
        delegates = listOf(
            TextEditDelegate(requireContext(), saveButton),
            ImportanceSpinnerDelegate(requireContext()) ,
            UntilDateDelegate(requireContext()),
            DeleteDelegate(requireContext(),
                EditToListCallback(navController),
                deleteFun)
        )
    }
    private fun initializeRecycler(view:View){
        lateinit var curItem :ToDoItemEntity
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                item.collect{
                    Log.d("equalityOld", viewModel.oldItem.toString())
                    curItem = it
                    checkEquality(curItem)
                }
            }
        }
        initializeDelegates()
        adapter = EditRecyclerAdapter(item, delegates)
        recyclerView.adapter = adapter
        exitButton = view.findViewById(R.id.close_button_collapsed)
        exitButton.setOnClickListener {
            navController.popBackStack()
        }
        saveButton.setOnClickListener {
            viewModel.addToDoItem(curItem)
            navController.popBackStack()
        }
    }

    fun checkEquality(curItem:ToDoItemEntity){
        if(curItem == viewModel.oldItem ||(curItem.text.isBlank() && !updated)){
            Log.d("equality", "true, curItem = $curItem")
            saveButton.isClickable = false
            saveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        }
        else {
            Log.d("equality", "false, curItem = $curItem")
            saveButton.isClickable = true
            saveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        }
    }


}