package com.example.itunesmusicsearch.ui.search.adapters

import com.example.itunesmusicsearch.data.database.Album

// Concrete realisation of Adapter for Search results albums list
class SearchResultsAlbumsListAdapter(clickListener: OnAlbumsListItemClickListener) :
    AlbumsListAdapter(clickListener) {
    override fun List<Album>.putInOrder() = this.sortedBy { it.name }
}
