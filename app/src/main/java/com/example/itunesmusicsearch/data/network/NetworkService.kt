package com.example.itunesmusicsearch.data.network

import com.example.itunesmusicsearch.data.database.Album
import com.example.itunesmusicsearch.data.database.Track

// Interface to interact with network data source
interface NetworkService {
    fun getAlbums(searchQuery: String): List<Album>?
    fun getAlbumTracks(albumId: Int): List<Track>?
}