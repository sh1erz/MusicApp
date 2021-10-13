package com.example.musicapp.ui.main.presenter

import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.data.entities.Track
import com.example.data_android.MusicRepository
import com.example.musicapp.databinding.TrackItemBinding
import com.example.musicapp.ui.adapters.OnTrackClickListener
import com.example.musicapp.ui.main.view.TrackRowView
import com.example.musicapp.ui.main.view.TrackView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainPresenter @Inject constructor(private val repository: MusicRepository) :
    BasePresenter<TrackView>(),
    OnTrackClickListener, TrackAdapterDataSource {

    private var tracks: List<Track> = listOf()

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

    override fun onTrackItemClick(track: Track, binding: TrackItemBinding) {
        val extras = FragmentNavigatorExtras(
            binding.picture to track.album.cover
        )
        view()?.showDetails(track, extras)
    }


}