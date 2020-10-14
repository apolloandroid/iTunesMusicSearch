package com.example.itunesmusicsearch.ui.albumdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.itunesmusicsearch.R
import com.example.itunesmusicsearch.data.database.Album
import com.example.itunesmusicsearch.databinding.FragmentAlbumDetailsBinding
import com.example.itunesmusicsearch.ui.MainActivity
import com.squareup.picasso.Picasso


class AlbumDetailsFragment : Fragment() {
    private lateinit var binding: FragmentAlbumDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_album_details, container, false)
        val album = arguments?.getSerializable(Album.SERIALIZABLE_TAG) as Album?
        setToolbar(album)
        showAlbumInfo(album)
        return binding.root
    }

    private fun setToolbar(album: Album?) {
        val activity = activity as MainActivity? ?: return
        activity.setSupportActionBar(binding.toolbar)
        binding.toolbar.title = album?.name + " - " + album?.artistName
    }

    private fun showAlbumInfo(album: Album?) {
        if (album != null) {
            Picasso.get().load(album.coverUrl).into(binding.imageAlbumCover)
            binding.listTracks.adapter = TracksListAdapter(album.tracks ?: listOf())
        }
    }
}