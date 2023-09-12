package com.example.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do_item" )
data class ToDoItem(@PrimaryKey(autoGenerate = true) var id:Int, var text:String,
                    @ColumnInfo(name = "importance") var importance: String,
                    @ColumnInfo("is_complete") var isComplete:Boolean )



