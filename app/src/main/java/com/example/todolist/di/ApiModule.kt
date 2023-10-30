package com.example.todolist.di

import com.example.todolist.data.network.DiskApiService
import com.example.todolist.data.network.interceptors.AuthTokenInterceptor
import com.example.todolist.data.repository.ApiRepositoryImpl
import com.example.todolist.domain.repository.ApiRepository
import com.example.todolist.domain.repository.TokenRepository
import com.example.todolist.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    @Provides
    @Singleton
    fun provideApiRepository(apiService: DiskApiService) : ApiRepository
    = ApiRepositoryImpl(apiService)
    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory
            = GsonConverterFactory.create(GsonBuilder().create())

    @Provides
    @Singleton
    fun provideOkHttp(authTokenInterceptor: AuthTokenInterceptor) : OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(authTokenInterceptor)
        .addInterceptor(HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()


    @Provides
    @Singleton
    fun provideAuthTokenInterceptor(tokenRepository: TokenRepository)
            = AuthTokenInterceptor(tokenRepository)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient,
                        gsonConverterFactory: GsonConverterFactory): DiskApiService
            = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(gsonConverterFactory)
        .build()
        .create(DiskApiService::class.java)

}

