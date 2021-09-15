package com.example.musicapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.MusicRepository
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.data.entities.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    ViewModel() {

    fun getArtists() = musicRepository.getSavedArtists()

    fun searchArtists(name: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val artists = musicRepository.searchArtists(name)
            musicRepository.addArtists(artists.data)
        } catch (ex: Exception) {
            Log.i(LOG, ex.message ?: "error searching artist")
        }
    }

    fun updateSuggestions(query: String): Observable<List<String>> {
        return Observable.create { e ->
            musicRepository.getSuggestions(query).enqueue(object : Callback<Search<Artist>> {
                override fun onResponse(
                    call: Call<Search<Artist>>,
                    response: Response<Search<Artist>>
                ) {
                    if (response.body() == null) return
                    e.onNext(response.body()!!.data.map { it.name })
                    e.onComplete()
                }

                override fun onFailure(call: Call<Search<Artist>>, t: Throwable) {
                    e.onError(t)
                }
            })

        }
    }


}


const val LOG = "my_logs"