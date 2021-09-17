package com.example.musicapp.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.MusicRepository
import com.example.musicapp.data.entities.Searchable
import com.example.musicapp.ui.main.LOG
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    ViewModel() {
    fun searchArtists(name: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val artists = musicRepository.searchArtists(name)
        } catch (ex: Exception) {
            Log.i(LOG, "${ex.stackTrace} -- " + ex.message ?: "error searching artist")
        }
    }

    fun search(query: String) = liveData<List<Searchable>> {
        try {
            val artists = musicRepository.searchArtists(query, limit = 3)
            val tracks = musicRepository.searchTracks(query)
            val list = mutableListOf<Searchable>()
            list.apply {
                addAll(artists.data)
                addAll(tracks.data)
            }
            emit(list)
        } catch (ex: Exception) {
            Log.i(LOG, "${ex.stackTrace} -- " + ex.message ?: "error searching artist")
        }
    }

    fun updateSuggestions(query: String): Observable<List<String>> {
        return Observable.create { e ->
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    Log.i(LOG, "updateSuggestions")
                    val artists = musicRepository.getArtistSuggestions(query).data.map{it.name}
                    val tracks = musicRepository.getTrackSuggestions(query).data.map { it.title }
                    withContext(Dispatchers.Main) {
                        e.onNext(artists + tracks)
                        e.onComplete()
                    }
                } catch (ex: java.lang.Exception) {
                    Log.i(LOG,
                        "updateSuggestions ex: ${ex.stackTrace} -- " + ex.message
                            ?: "error getting suggestions"
                    )
                    e.onError(ex)
                }
            }
            /*musicRepository.getArtistSuggestions(query).enqueue(object :
                Callback<Suggestion<SuggestArtist>> {
                override fun onResponse(
                    call: Call<Suggestion<SuggestArtist>>,
                    response: Response<Suggestion<SuggestArtist>>
                ) {
                    if (response.body() == null) return
                    e.onNext(response.body()!!.data.map { it.name })
                    e.onComplete()
                }

                override fun onFailure(call: Call<Suggestion<SuggestArtist>>, t: Throwable) {
                    e.onError(t)
                }
            })*/

        }
    }
}