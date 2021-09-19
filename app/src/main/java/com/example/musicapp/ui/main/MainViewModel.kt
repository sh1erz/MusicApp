package com.example.musicapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.MusicRepository
import com.example.musicapp.data.entities.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
/*

@HiltViewModel
class MainViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    ViewModel() {
        fun getListenedTracks() : LiveData<List<Track>> = musicRepository.getAllTracks()
}
*/


const val LOG = "my_logs"