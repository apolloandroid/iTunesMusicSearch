package com.example.itunesmusicsearch.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumsDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(album: Album)

    @Query("SELECT * FROM Albums")
    fun getAllAlbums(): LiveData<List<Album>>
}