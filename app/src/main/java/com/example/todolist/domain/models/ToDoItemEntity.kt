package com.example.todolist.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.utils.Importance
import java.util.Date

@Entity(tableName = "to_do_item" )
  data class ToDoItemEntity (@PrimaryKey(autoGenerate = true)
                          var id:Int = 0,
                          var text:String = "",
                          @ColumnInfo(name = "importance")
                          var importance: String = Importance.NONE.text,
                          @ColumnInfo(name = "is_complete")
                          var isComplete:Boolean = false,
                          @ColumnInfo(name = "until_date")
                          var untilDate: Date? = null){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToDoItemEntity

        if (id != other.id) return false
        if (text.trim() != other.text.trim()) return false
        if (importance != other.importance) return false
        if (isComplete != other.isComplete) return false
        if (untilDate != other.untilDate) return false

        return true
    }

}








