package com.example.musicapp.ui.main.model

import androidx.lifecycle.LiveData
import com.example.musicapp.data.entities.Track

interface MainModel {
    fun getListenedTracks(): LiveData<List<Track>>
}