package com.example.todolist.di

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.example.todolist.db.ToDoItemDao
import com.example.todolist.db.ToDoItemEntity
import com.example.todolist.db.ToDoItemsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context)
    : ToDoItemsDB = Room.databaseBuilder(context,
        ToDoItemsDB::class.java,
        "to_do_list_db")
        .build()

    @Provides
    @Singleton
    fun provideDao(db : ToDoItemsDB): ToDoItemDao = db.getDao()

    @Provides
    @Singleton
    fun provideToDoItemEntity() = ToDoItemEntity()

}