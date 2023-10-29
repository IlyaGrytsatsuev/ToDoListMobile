package com.example.todolist.di

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.db.ToDoItemDao
import com.example.todolist.data.db.ToDoItemsDB
import com.example.todolist.data.repository.DatabaseRepositoryImpl
import com.example.todolist.domain.models.ToDoItemEntity
import com.example.todolist.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

    @Singleton
    @Provides
    fun provideRepository(dao: ToDoItemDao) : DatabaseRepository{
        return DatabaseRepositoryImpl(dao)
    }

}