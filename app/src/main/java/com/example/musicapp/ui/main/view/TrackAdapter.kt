package com.example.musicapp.ui.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entities.Track
import com.example.musicapp.databinding.TrackItemBinding
import com.example.musicapp.ui.adapters.OnTrackClickListener
import com.example.musicapp.ui.main.presenter.TrackAdapterDataSource
import com.squareup.picasso.Picasso

class TrackAdapter(
    private val presenter: TrackAdapterDataSource
) :
    RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        presenter.onBindTrackRowView(position, holder)
    }

    override fun getItemCount(): Int =
        presenter.getItemsCount()

    class TrackViewHolder(private val binding: TrackItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        TrackRowView {
        override fun setView(listener: OnTrackClickListener, track: Track) {
            with(binding){
                Picasso.with(picture.context)
                    .load(track.album.cover_medium)
                    .into(picture)
                tvTitle.text = track.title
                tvArtist.text = track.artist.name
                constraint.setOnClickListener { listener.onTrackItemClick(track) }
            }
        }
    }
}