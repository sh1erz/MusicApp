package com.example.musicapp.ui.main.presenter

import com.example.musicapp.data.entities.Track
import com.example.musicapp.ui.main.view.TrackRowView


interface IPresenter {
    fun onBindTrackRowView(position :Int, holder: TrackRowView)
    fun loadTrackHistory()
    fun getItemsCount():Int
}