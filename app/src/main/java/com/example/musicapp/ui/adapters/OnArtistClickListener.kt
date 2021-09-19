package com.example.musicapp.ui.adapters

import com.example.musicapp.data.entities.Artist

interface OnArtistClickListener {
    fun onArtistItemClick(artist: Artist)
}