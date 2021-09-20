package com.example.musicapp.ui.main.view

import com.example.musicapp.data.entities.Track
import com.example.musicapp.ui.adapters.OnTrackClickListener

interface TrackRowView {
    fun setView(listener: OnTrackClickListener, track: Track)
}