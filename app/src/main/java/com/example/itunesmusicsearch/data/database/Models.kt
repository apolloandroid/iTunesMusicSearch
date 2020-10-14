package com.example.itunesmusicsearch.data.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Albums")
data class Album(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("collectionId")
    @Expose
    val collectionId: Int,
    @SerializedName("artistName")
    @Expose
    val artistName: String,
    @SerializedName("trackCount")
    @Expose
    val trackCount: Int,
    @SerializedName("collectionName")
    @Expose
    val name: String,
    @SerializedName("artworkUrl100")
    @Expose
    val coverUrl: String,
) : Serializable {
    @Ignore
    var tracks: List<Track>? = null

    companion object {
        val SERIALIZABLE_TAG = "Album"
    }
}

data class Track(
    @SerializedName("wrapperType")
    @Expose
    val type: String,
    @SerializedName("trackName")
    @Expose
    val trackName: String,
    @SerializedName("trackNumber")
    @Expose
    val trackNumber: Int,
    @SerializedName("trackTimeMillis")
    @Expose
    val trackTimeMillis: Int
)