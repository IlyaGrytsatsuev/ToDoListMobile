package com.example.todolist.presenter.callbacks

import androidx.navigation.NavController

class EditToListCallback(val navController: NavController) {
    fun onClick(){
        navController.popBackStack()

    }
}