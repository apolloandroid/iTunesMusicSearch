package com.example.itunesmusicsearch.data.network

import com.example.itunesmusicsearch.data.database.Album
import com.example.itunesmusicsearch.data.database.Track
import retrofit2.Retrofit
import javax.inject.Inject

// Concrete realisation of NetworkService Interface
class ITunesNetworkService @Inject constructor(retrofit: Retrofit) : NetworkService {
    private val COUNTRY_CODE = "us"
    private val CONTENT_TYPE = "music"
    private val ENTITY_TYPE_ALBUM = "album"
    private val ENTITY_TYPE_SONG = "song"

    private val iTunesApi = retrofit.create(ITunesApi::class.java)

    override fun getAlbums(searchQuery: String): List<Album>? {
        val response =
            iTunesApi.getAlbums(searchQuery, COUNTRY_CODE, CONTENT_TYPE, ENTITY_TYPE_ALBUM)
                .execute()
        return response.body()?.results
    }

    override fun getAlbumTracks(albumId: Int): List<Track>? {
        val response = iTunesApi.getTracks(albumId.toString(), ENTITY_TYPE_SONG).execute()
        return response.body()?.results
    }
}