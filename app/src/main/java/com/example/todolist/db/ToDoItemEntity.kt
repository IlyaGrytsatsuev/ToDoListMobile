package com.example.todolist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Calendar
import java.util.Date

@Entity(tableName = "to_do_item" )
data class ToDoItemEntity(@PrimaryKey(autoGenerate = true)
                          var id:Int = 0,
                          var text:String = "",
                          @ColumnInfo(name = "importance")
                          var importance: String = "",
                          @ColumnInfo(name = "is_complete")
                          var isComplete:Boolean = false,
                          @ColumnInfo(name = "until_date")
                          var untilDate: Date? = null)



