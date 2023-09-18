package com.example.todolist.callbacks

import androidx.navigation.NavController
import com.example.todolist.ui.*

class RecyclerToEditCallback(val navController: NavController) : RecyclerOnClickCallBack {
     override fun onClick(id:Int) {
         var action = ListFragmentDirections.actionListFragmentToToDoItemFragment(id)
         navController.navigate(action)

     }
 }