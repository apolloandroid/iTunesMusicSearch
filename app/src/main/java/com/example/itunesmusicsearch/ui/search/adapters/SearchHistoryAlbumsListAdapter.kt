package com.example.itunesmusicsearch.ui.search.adapters

import com.example.itunesmusicsearch.data.database.Album

// Concrete realisation of Adapter for Search history albums list
class SearchHistoryAlbumsListAdapter(clickListener: OnAlbumsListItemClickListener) :
    AlbumsListAdapter(clickListener) {
    override fun List<Album>.putInOrder() = this.reversed()
}