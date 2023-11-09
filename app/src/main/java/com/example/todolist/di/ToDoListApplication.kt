package com.example.todolist.di

import android.app.Application
import android.content.Context


class ToDoListApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory()
            .create(context = this)
    }

    val Context.appComponent: AppComponent?
        get() = when (this) {
            is ToDoListApplication -> appComponent
            else -> this.applicationContext.appComponent
        }
}