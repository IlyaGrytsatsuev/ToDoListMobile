package com.example.todolist.di

import android.content.Context
import com.example.todolist.data.network.TokenRepositoryImpl
import com.example.todolist.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenDataStoreModule {

    @Singleton
    @Provides
    fun provideTokenRepository(@ApplicationContext context: Context) : TokenRepository
    = TokenRepositoryImpl(context)
}