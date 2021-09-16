package com.example.musicapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.entities.Track
import com.example.musicapp.databinding.TrackItemBinding
import com.example.musicapp.ui.adapters.AdapterItemListener
import com.squareup.picasso.Picasso

class TrackAdapter(val tracks: MutableList<Track>, val listener: AdapterItemListener) :
    RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        with(tracks[position]) {
            holder.binding.apply {
                tvTitle.text = title
                Picasso.with(picture.context)
                    .load(album.cover_small)
                    .into(picture)
                constraint.setOnClickListener {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = tracks.size

    class TrackViewHolder(val binding: TrackItemBinding) : RecyclerView.ViewHolder(binding.root)
}