package com.example.musicapp.data

import androidx.lifecycle.LiveData
import com.example.musicapp.data.db.ArtistDao
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.retrofit.DeezerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MusicRepository @Inject constructor(
    private val artistDao: ArtistDao,
    private val dataSource: DeezerService
) {
     fun getSavedArtists(): LiveData<List<Artist>> = artistDao.getAll()

    suspend fun addArtists(artists: List<Artist>) = withContext(Dispatchers.IO) {
        val deferred = async { deleteAllArtists() }
        deferred.await()
        artistDao.insertArtists(artists)
    }

    private fun deleteAllArtists() = artistDao.clear()

    suspend fun searchArtists(name: String) = dataSource.searchArtists(name)

    fun getSuggestions(name: String) = dataSource.searchArtistsRxJava(name)
}