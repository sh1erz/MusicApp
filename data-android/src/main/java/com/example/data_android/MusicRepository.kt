package com.example.data_android

import androidx.lifecycle.LiveData
import com.example.retrofit.DeezerService
import com.example.data.entities.Track
import com.example.db.TrackDao
import javax.inject.Inject


class MusicRepository @Inject constructor(
    private val trackDao: TrackDao,
    private val dataSource: DeezerService
) {

    suspend fun searchArtists(name: String, limit: Int = 25, index: Int = 0) =
        dataSource.searchArtists(name, limit, index)

    fun getAllTracks(): LiveData<List<Track>> = trackDao.getAllTracks()

    fun getTrackById(id:Long): Track = trackDao.getTrackById(id)

    fun addTrackUpIfExists(track: Track) =
        trackDao.insertWithTimestamp(track)


    suspend fun searchTracks(title: String, limit: Int = 25, index: Int = 0) =
        dataSource.searchTracks(title, limit, index)

    suspend fun getArtistSuggestions(name: String, limit: Int = 2) =
        dataSource.searchArtistsSuggestions(name, limit)
    suspend fun getTrackSuggestions(title: String, limit: Int = 3) =
        dataSource.searchTracksSuggestions(title, limit)
}