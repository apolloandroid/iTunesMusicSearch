package com.example.itunesmusicsearch.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ITunesApi {
    companion object {
        val baseUrl = "https://itunes.apple.com/"
    }

    @GET("search")
    fun getAlbums(
        @Query("term") term: String,
        @Query("country") country: String,
        @Query("media") type: String,
        @Query("entity") entityType: String
    ): Call<SearchAlbumsResponse>

    @POST("lookup")
    fun getTracks(
        @Query("id") id: String,
        @Query("entity") entity: String
    ): Call<GetTracksResponse>
}