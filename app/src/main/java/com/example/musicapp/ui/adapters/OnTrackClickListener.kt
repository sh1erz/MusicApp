package com.example.musicapp.ui.adapters

import com.example.data.entities.Track
import com.example.musicapp.databinding.TrackItemBinding

interface OnTrackClickListener {
    fun onTrackItemClick(track: Track, binding: TrackItemBinding)
}