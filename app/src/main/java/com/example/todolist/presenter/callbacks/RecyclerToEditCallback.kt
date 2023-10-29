package com.example.todolist.presenter.callbacks

import androidx.navigation.NavController
import com.example.todolist.presenter.ui.ListFragmentDirections

class RecyclerToEditCallback(val navController: NavController) : RecyclerOnClickCallBack {
     override fun onClick(id:Int) {
         var action = ListFragmentDirections.actionListFragmentToToDoItemFragment(id)
         navController.navigate(action)

     }
 }