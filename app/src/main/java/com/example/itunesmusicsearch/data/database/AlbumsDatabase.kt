package com.example.itunesmusicsearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class AlbumsDatabase : RoomDatabase() {

    abstract val albumsDataBaseDao: AlbumsDatabaseDao

    companion object {
        val ALBUMS_DATABASE_NAME = "Albums"

        @Volatile
        private var INSTANCE: AlbumsDatabase? = null

        fun getInstance(context: Context): AlbumsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext, AlbumsDatabase::class.java,
                            ALBUMS_DATABASE_NAME
                        ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}