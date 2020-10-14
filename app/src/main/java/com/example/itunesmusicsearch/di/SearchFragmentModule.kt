package com.example.itunesmusicsearch.di

import android.content.Context
import com.example.itunesmusicsearch.data.ITunesRepository
import com.example.itunesmusicsearch.data.Repository
import com.example.itunesmusicsearch.data.database.AlbumsDatabase
import com.example.itunesmusicsearch.data.database.AlbumsDatabaseDao
import com.example.itunesmusicsearch.data.network.ITunesApi
import com.example.itunesmusicsearch.data.network.ITunesNetworkService
import com.example.itunesmusicsearch.data.network.NetworkService
import com.example.itunesmusicsearch.ui.search.SearchViewModel
import com.example.itunesmusicsearch.ui.search.SearchViewModelFactory
import com.example.itunesmusicsearch.utility.ConnectionStatus
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class SearchFragmentModule(private val context: Context) {

    @Provides
    fun provideSearchViewModel(
        repository: Repository,
        connectionStatus: ConnectionStatus
    ): SearchViewModel {
        return SearchViewModelFactory(
            repository,
            connectionStatus
        ).create(SearchViewModel::class.java)
    }

    @Reusable
    @Provides
    fun provideRepository(repository: ITunesRepository): Repository = repository

    @Provides
    fun provideAlbumsDatabaseDao(database: AlbumsDatabase): AlbumsDatabaseDao {
        return database.albumsDataBaseDao
    }

    @Reusable
    @Provides
    fun provideAlbumsDatabase(): AlbumsDatabase {
        return AlbumsDatabase.getInstance(context)
    }

    @Reusable
    @Provides
    fun provideNetworkService(networkService: ITunesNetworkService): NetworkService =
        networkService

    @Reusable
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ITunesApi.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

    @Reusable
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Reusable
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60, TimeUnit.SECONDS).build()

    @Reusable
    @Provides
    fun provideConnectionStatus(): ConnectionStatus = ConnectionStatus(context)
}