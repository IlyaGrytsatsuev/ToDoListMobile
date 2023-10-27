package com.example.todolist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.todolist.utils.Importance
import com.example.todolist.utils.ItemState
import java.util.Date

@Entity(tableName = "to_do_item" )
  data class ToDoItemEntity (@PrimaryKey(autoGenerate = true)
                             var id:Int = 0,
                             @ColumnInfo(name = "common_id")
                             var hash :String = "",
                             @ColumnInfo(name = "text")
                             var text:String = "",
                             @ColumnInfo(name = "importance")
                             var importance: String = Importance.NONE.text,
                             @ColumnInfo(name = "is_complete")
                             var isComplete:Boolean = false,
                             @ColumnInfo(name = "until_date")
                             var untilDate: Date? = null,
                             @ColumnInfo(name = "state")
                             var state:String = ItemState.ON_UPLOAD.text,
                             @TypeConverters(LongToIso8601::class)
                             @ColumnInfo(name = "modified")
                             var modified:Long = 0){

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

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + text.hashCode()
        result = 31 * result + importance.hashCode()
        result = 31 * result + isComplete.hashCode()
        result = 31 * result + (untilDate?.hashCode() ?: 0)
        return result
    }


}








