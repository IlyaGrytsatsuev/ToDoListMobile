package com.example.todolist.di

import com.example.todolist.data.network.TokenRepositoryImpl
import com.example.todolist.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module


@Module
interface TokenDataStoreModule {
    @Binds
    fun provideTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl) : TokenRepository

}