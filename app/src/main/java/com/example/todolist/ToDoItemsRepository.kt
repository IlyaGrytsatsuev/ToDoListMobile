package com.example.todolist

import java.util.ArrayList
import kotlin.concurrent.Volatile

class ToDoItemsRepository() {
    @Volatile
    private var itemsList: MutableList<ToDoItem> = ArrayList<ToDoItem>()
    companion object {
        @Volatile
        private var instance:ToDoItemsRepository? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ToDoItemsRepository().also {it.initDemoList()
                    instance = it
                }
            }

    }

    fun getItemsList() = this.itemsList

    private fun initDemoList(){
        for(i in 0..20)
            addNewItem(ToDoItem(i,
                "Do the work jkcnsklnsaklnklcnkldnklndsclkcsdlkcmlksmcklermflkmwklmkldsmlkmdslkmcds",
                Importance.HIGH.text, false))


    }
    fun getItemById(id:Int) = synchronized(this){ itemsList.find{it.id == id}}


    fun addNewItem(toDoItem: ToDoItem) {
        this.itemsList.add(toDoItem)
    }



}
