package com.example.musicapp.data

import androidx.lifecycle.LiveData
import com.example.musicapp.data.db.TrackDao
import com.example.musicapp.data.entities.Track
import com.example.musicapp.retrofit.DeezerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MusicRepository @Inject constructor(
    private val trackDao: TrackDao,
    private val dataSource: DeezerService
) {
    //fun getSavedArtists(): LiveData<List<Artist>> = artistDao.getAll()

    /* suspend fun addArtists(artists: List<Artist>) = withContext(Dispatchers.IO) {
         val deferred = async { deleteAllArtists() }
         deferred.await()
         artistDao.insertArtists(artists)
     }*/


    //private fun deleteAllArtists() = artistDao.clear()

    suspend fun searchArtists(name: String, limit: Int = 25, index: Int = 0) =
        dataSource.searchArtists(name, limit, index)

    fun getAllTracks(): LiveData<List<Track>> = trackDao.getAllTracks()

    suspend fun addTrack(track: Track) = withContext(Dispatchers.IO) {
        trackDao.insertTrack(track)
    }

    suspend fun searchTracks(title: String, limit: Int = 25, index: Int = 0) =
        dataSource.searchTracks(title, limit, index)

    suspend fun getArtistSuggestions(name: String, limit: Int = 2) =
        dataSource.searchArtistsSuggestions(name, limit)
    suspend fun getTrackSuggestions(title: String, limit: Int = 3) =
        dataSource.searchTracksSuggestions(title, limit)
}