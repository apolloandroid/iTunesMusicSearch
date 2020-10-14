package com.example.itunesmusicsearch.ui.search.adapters

import com.example.itunesmusicsearch.data.database.Album
// Interface, which defines Albums list item click event
interface OnAlbumsListItemClickListener {
    fun onItemClick(album: Album)
}