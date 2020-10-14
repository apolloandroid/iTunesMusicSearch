package com.example.itunesmusicsearch.data

import androidx.lifecycle.LiveData
import com.example.itunesmusicsearch.data.database.Album
import com.example.itunesmusicsearch.data.database.AlbumsDatabaseDao
import com.example.itunesmusicsearch.data.database.Track
import com.example.itunesmusicsearch.data.network.NetworkService
import javax.inject.Inject

// Concrete realisation of Repository interface
class ITunesRepository @Inject constructor(
    private val networkService: NetworkService,
    private val albumsDatabaseDao: AlbumsDatabaseDao
) : Repository {

    override val searchHistory: LiveData<List<Album>>
        get() = albumsDatabaseDao.getAllAlbums()

    override suspend fun getAlbums(searchQuery: String): List<Album>? {
        return networkService.getAlbums(searchQuery)
    }

    override suspend fun getTracks(albumId: Int): List<Track>? {
        return networkService.getAlbumTracks(albumId).onlyTracks()
    }

    private fun List<Track>?.onlyTracks(): List<Track>? {
        val TRACK_WRAPPER_TYPE = "track"
        return this?.filter { it.type == TRACK_WRAPPER_TYPE }
    }


    override suspend fun addAlbumToSearchHistory(album: Album) {
        albumsDatabaseDao.insertAlbum(album)
    }
}