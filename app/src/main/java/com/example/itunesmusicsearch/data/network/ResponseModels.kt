package com.example.itunesmusicsearch.data.network

import com.example.itunesmusicsearch.data.database.Album
import com.example.itunesmusicsearch.data.database.Track
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Models of network responses
data class SearchAlbumsResponse(
    @SerializedName("resultCount")
    @Expose
    val resultCount: Int,
    @SerializedName("results")
    @Expose
    val results: List<Album>
)

data class GetTracksResponse(
    @SerializedName("results")
    @Expose
    val results: List<Track>
)