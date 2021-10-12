package com.example.musicapp.fakes

import com.example.retrofit.DeezerService
import com.example.data.entities.*

class FakeDeezerService : DeezerService {
    override suspend fun searchArtists(
        artistName: String,
        limit: Int,
        index: Int
    ): Search<Artist> =
        Search(listOf(), 0, "")

    override suspend fun searchTracks(
        trackName: String,
        limit: Int,
        index: Int
    ): Search<Track> =
        Search(listOf(), 0, "")


    override suspend fun searchArtistsSuggestions(
        artistName: String,
        limit: Int
    ): Suggestion<SuggestArtist> =
        Suggestion(listOf())

    override suspend fun searchTracksSuggestions(
        artistName: String,
        limit: Int
    ): Suggestion<SuggestTrack> =
        Suggestion(listOf())

    override suspend fun getReleaseAlbums(genreId: Int): Editorial<Album> = Editorial(listOf(test_album))
    override suspend fun findAlbumTracks(id: Long): Editorial<Track> = Editorial(TRACKS_DATASET)
}