package com.example.musicapp.ui.main.presenter

import com.example.musicapp.ui.main.view.TrackRowView

interface TrackAdapterDataSource {
    fun onBindTrackRowView(position: Int, holder: TrackRowView)
    fun getItemsCount(): Int
}