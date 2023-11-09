package com.example.todolist.di

import android.content.Context
import com.example.todolist.data.network.DiskApiService
import com.example.todolist.data.network.interceptors.AuthTokenInterceptor
import com.example.todolist.data.repository.ApiRepositoryImpl
import com.example.todolist.domain.repository.ApiRepository
import com.example.todolist.domain.repository.TokenRepository
import com.example.todolist.presenter.ui.ListFragment
import com.example.todolist.presenter.ui.MainActivity
import com.example.todolist.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Component(modules = [ApiModule::class,
    TokenDataStoreModule::class,
    DatabaseModule::class])
interface AppComponent{
    @Component.Factory
    interface ComponentBuilder{
        fun create(@BindsInstance context: Context):AppComponent
    }
    fun inject(activity: MainActivity)
    fun inject(fragment: ListFragment)
}

@Module
interface ApiModule{
    @Binds
    fun provideApiRepository(apiRepositoryImpl: ApiRepositoryImpl) : ApiRepository

    companion object {

        @Provides
        fun provideGsonConverterFactory(): GsonConverterFactory
            = GsonConverterFactory.create(GsonBuilder().create())

        @Provides
        fun provideOkHttp(authTokenInterceptor: AuthTokenInterceptor) : OkHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(authTokenInterceptor)
            .addInterceptor(HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        @Provides
        fun provideAuthTokenInterceptor(tokenRepository: TokenRepository)
                = AuthTokenInterceptor(tokenRepository)

        @Provides
        fun provideRetrofit(client: OkHttpClient,
                            gsonConverterFactory: GsonConverterFactory): DiskApiService
                = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(DiskApiService::class.java)
    }
}


