package com.example.musicapp.ui.main.presenter

import com.example.musicapp.data.entities.Track
import com.example.musicapp.ui.adapters.OnTrackClickListener
import com.example.musicapp.ui.main.model.MainModel
import com.example.musicapp.ui.main.view.IView
import com.example.musicapp.ui.main.view.TrackRowView

class MainPresenter(val view: IView, private val model: MainModel) : IPresenter,
    OnTrackClickListener {

    private var tracks: List<Track> = listOf()
    init {
        model.getListenedTracks().observe(view.getViewLifecycleOwner()){ tracks ->
            this.tracks = tracks
            view.showList()
        }
    }

    override fun onBindTrackRowView(position: Int, holder: TrackRowView) {
        holder.setView(this, tracks[position])

    }

    override fun getItemsCount() = tracks.size

    override fun loadTrackHistory() {
    }

    override fun onTrackItemClick(track: Track) {
        view.showDetails(track)
    }


}