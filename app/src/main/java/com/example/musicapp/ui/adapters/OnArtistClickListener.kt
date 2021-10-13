package com.example.musicapp.ui.adapters

import com.example.data.entities.Artist
import com.example.musicapp.databinding.ArtistItemBinding

interface OnArtistClickListener {
    fun onArtistItemClick(artist: Artist, binding: ArtistItemBinding)
}