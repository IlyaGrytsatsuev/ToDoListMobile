package com.example.todolist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Entity(tableName = "to_do_item" )
class ToDoItemEntity (@PrimaryKey(autoGenerate = true)
                          var id:Int = 0,
                          var text:String = "",
                          @ColumnInfo(name = "importance")
                          var importance: String = "",
                          @ColumnInfo(name = "is_complete")
                          var isComplete:Boolean = false,
                          @ColumnInfo(name = "until_date")
                          var untilDate: Date? = null){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToDoItemEntity

        if (id != other.id) return false
        if (text != other.text) return false
        if (importance != other.importance) return false
        if (isComplete != other.isComplete) return false
        if (untilDate != other.untilDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + text.hashCode()
        result = 31 * result + importance.hashCode()
        result = 31 * result + isComplete.hashCode()
        result = 31 * result + (untilDate?.hashCode() ?: 0)
        return result
    }
}



