package com.example.todolist.presenter.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.di.ToDoListApplication
import com.example.todolist.presenter.viewModel.DataViewModel
import com.example.todolist.presenter.viewModel.DataViewModelFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: DataViewModelFactory

    private var viewModel: DataViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as ToDoListApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[DataViewModel::class.java]
    }


    override fun onRestart() {
        super.onRestart()
        Log.d("life", "restart")

    }

    override fun onStop() {
        super.onStop()
        Log.d("life", "stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("life", "destroy")

    }

}