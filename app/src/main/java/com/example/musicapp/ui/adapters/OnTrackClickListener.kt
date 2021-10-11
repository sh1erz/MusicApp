package com.example.musicapp.ui.adapters

import com.example.data.entities.Track

interface OnTrackClickListener {
    fun onTrackItemClick(track: com.example.data.entities.Track)
}