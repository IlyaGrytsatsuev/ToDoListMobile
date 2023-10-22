package com.example.todolist.callbacks

import androidx.navigation.NavController
import com.example.todolist.ui.ToDoItemFragmentDirections

class EditToListCallback(val navController: NavController) {
    fun onClick(){
        navController.popBackStack()

    }
}