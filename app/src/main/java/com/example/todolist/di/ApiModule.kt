package com.example.todolist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.todolist.network.TokenManager
import com.example.todolist.network.interceptors.AuthTokenInterceptor
import com.example.todolist.network.services.DiskApiService
import com.example.todolist.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

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
    fun provideTokenManager(@ApplicationContext context: Context)
            = TokenManager(context)


    @Provides
    @Singleton
    fun provideAuthTokenInterceptor(tokenManager: TokenManager)
            = AuthTokenInterceptor(tokenManager)

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