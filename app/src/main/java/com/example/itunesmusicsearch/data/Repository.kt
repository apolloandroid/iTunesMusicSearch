package com.example.itunesmusicsearch.data

import androidx.lifecycle.LiveData
import com.example.itunesmusicsearch.data.database.Album
import com.example.itunesmusicsearch.data.database.Track

// Interface to interact with data sources
interface Repository {
    val searchHistory: LiveData<List<Album>>
    suspend fun getAlbums(searchQuery: String): List<Album>?
    suspend fun getTracks(albumId: Int): List<Track>?
    suspend fun addAlbumToSearchHistory(album: Album)
}