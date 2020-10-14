package com.example.itunesmusicsearch.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesmusicsearch.data.database.Album
import com.example.itunesmusicsearch.databinding.AlbumsListItemBinding
import com.squareup.picasso.Picasso

// Abstract class, which defines Albums list presentation
abstract class AlbumsListAdapter(private val clickListener: OnAlbumsListItemClickListener) :
    ListAdapter<Album, AlbumsListAdapter.AlbumViewHolder>(AlbumsDiffCallBack()) {

    private val albumsList = mutableListOf<Album>()

    abstract fun List<Album>.putInOrder(): List<Album>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AlbumsListItemBinding.inflate(layoutInflater, parent, false)
        return AlbumViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumsList[position])
    }

    override fun getItemCount() = albumsList.size

    fun refreshAlbums(albums: List<Album>) {
        this.albumsList.clear()
        this.albumsList.addAll(albums.putInOrder())
        notifyDataSetChanged()
    }

    class AlbumViewHolder(
        private val binding: AlbumsListItemBinding,
        private val clickListener: OnAlbumsListItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentAlbum: Album) {
            binding.album = currentAlbum
            Picasso.get().load(currentAlbum.coverUrl)
                .into(binding.imageAlbumCover)
            binding.root.setOnClickListener { clickListener.onItemClick(currentAlbum) }
        }
    }

    private class AlbumsDiffCallBack : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
            oldItem.name == newItem.name
    }
}