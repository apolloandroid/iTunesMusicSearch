package com.example.itunesmusicsearch.ui.albumdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesmusicsearch.data.database.Track
import com.example.itunesmusicsearch.databinding.TracksListItemBinding

class TracksListAdapter(private val tracksList: List<Track>) :
    ListAdapter<Track, TracksListAdapter.TrackViewHolder>(TracksDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TracksListItemBinding.inflate(layoutInflater, parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val currentTrack = tracksList[position]
        holder.bind(currentTrack)
    }

    override fun getItemCount() = tracksList.size

    class TrackViewHolder(private val binding: TracksListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentTrack: Track) {
            binding.textTrackName.text = currentTrack.trackName
            binding.textTrackNumber.text = currentTrack.trackNumber.toString()
            binding.textTrackDuration.text = getTrackDuration(currentTrack.trackTimeMillis)
        }

        private fun getTrackDuration(trackTimeMillis: Int): String {
            val totalTrackDurationSeconds = trackTimeMillis / 1000
            val trackDurationMinutes = totalTrackDurationSeconds / 60
            val trackDurationSeconds = totalTrackDurationSeconds % 60
            return String.format("%d:%02d", trackDurationMinutes, trackDurationSeconds)
        }
    }

    private class TracksDiffCallBack : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
            oldItem.trackNumber == newItem.trackNumber

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
            oldItem.trackName == newItem.trackName
    }
}