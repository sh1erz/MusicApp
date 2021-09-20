package com.example.musicapp.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.MusicRepository
import com.example.musicapp.data.entities.Searchable
import com.example.musicapp.data.entities.Track
import com.example.musicapp.ui.main.model.LOG
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    ViewModel() {

    private val _searchedList = MutableLiveData<List<Searchable>>(listOf())
    val searchedList
        get() = _searchedList
    val publishSubject: PublishSubject<String> = PublishSubject.create()

    fun getTrackById(id:Long) = musicRepository.getTrackById(id)

    fun addTrack(track: Track) =
        viewModelScope.launch(Dispatchers.IO) { musicRepository.addTrackUpIfExists(track) }

    fun search(query: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val artists = musicRepository.searchArtists(query, limit = 3)
            val tracks = musicRepository.searchTracks(query)
            val list = mutableListOf<Searchable>()
            list.apply {
                addAll(artists.data)
                addAll(tracks.data)
            }
            _searchedList.postValue(list)
        } catch (ex: Exception) {
            Log.i(LOG, "search: ${ex.message}")
        }
    }

    fun updateSuggestions(query: String): Observable<List<String>> {
        return Observable.create { e ->
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val artists = musicRepository.getArtistSuggestions(query).data.map{it.name}
                    val tracks = musicRepository.getTrackSuggestions(query).data.map { it.title }
                    withContext(Dispatchers.Main) {
                        e.onNext(artists + tracks)
                        e.onComplete()
                    }
                } catch (ex: java.lang.Exception) {
                    Log.i(LOG, "updateSuggestions: ${ex.message}")
                    e.onError(ex)
                }
            }

        }
    }
}