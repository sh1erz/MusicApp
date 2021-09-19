package com.example.musicapp.ui.adapters

import com.example.musicapp.data.entities.Track

interface OnTrackClickListener {
    fun onTrackItemClick(track: Track)
}