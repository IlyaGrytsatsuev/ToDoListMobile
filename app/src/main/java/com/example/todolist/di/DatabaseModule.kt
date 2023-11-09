package com.example.todolist.di

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.db.ToDoItemDao
import com.example.todolist.data.db.ToDoItemsDB
import com.example.todolist.data.repository.DatabaseRepositoryImpl
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.domain.repository.DatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DatabaseModule {

    @Binds
    fun provideRepository(databaseRepositoryImpl: DatabaseRepositoryImpl): DatabaseRepository

    companion object {
        @Provides
        fun provideDB(context: Context)
                : ToDoItemsDB = Room.databaseBuilder(
            context,
            ToDoItemsDB::class.java,
            "to_do_list_db"
        )
            .build()

        @Provides
        fun provideDao(db: ToDoItemsDB): ToDoItemDao = db.getDao()

        @Provides
        fun provideToDoItemEntity() = ToDoItemEntity()

    }
}
