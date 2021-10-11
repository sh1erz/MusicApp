package com.example.musicapp.ui.main.presenter

import com.example.data_android.MusicRepository
import com.example.musicapp.ui.adapters.OnTrackClickListener
import com.example.musicapp.ui.main.view.TrackRowView
import com.example.musicapp.ui.main.view.TrackView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainPresenter @Inject constructor(private val repository: MusicRepository) :
    BasePresenter<TrackView>(),
    OnTrackClickListener, TrackAdapterDataSource {

    private var tracks: List<com.example.data.entities.Track> = listOf()

    fun loadTracks(){
        view()?.let { view ->
            repository.getAllTracks()
                .observe(view.getViewLifecycleOwner()) { tracks ->
                    this.tracks = tracks
                    view.showList()
                }
        }
    }

    override fun onBindTrackRowView(position: Int, holder: TrackRowView) {
        holder.setView(this, tracks[position])
    }

    override fun getItemsCount() = tracks.size

    override fun onTrackItemClick(track: com.example.data.entities.Track) {
        view()?.showDetails(track)
    }


}