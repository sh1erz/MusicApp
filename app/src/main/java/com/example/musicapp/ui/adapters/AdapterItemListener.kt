package com.example.musicapp.ui.adapters

import com.example.musicapp.data.entities.Artist
import com.example.musicapp.data.entities.Track

interface AdapterItemListener {
    fun onArtistItemClick(artist: Artist)
    fun onTrackItemClick(track: Track)
}
