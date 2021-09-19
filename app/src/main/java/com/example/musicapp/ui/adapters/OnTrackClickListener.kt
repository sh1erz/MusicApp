package com.example.musicapp.ui.adapters

import com.example.musicapp.data.entities.Track

interface OnTrackClickListener {
    fun onTrackItemClick(position: Int)
}