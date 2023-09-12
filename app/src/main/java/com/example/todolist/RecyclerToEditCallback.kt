package com.example.todolist

import androidx.navigation.NavController
import androidx.navigation.NavOptions

class RecyclerToEditCallback(val navController: NavController) : RecyclerOnClickCallBack {
     override fun onClick(id:Int) {
         var action = ListFragmentDirections.actionListFragmentToToDoItemFragment(id)
         navController.navigate(action)

     }
 }