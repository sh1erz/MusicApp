package com.example.musicapp.fakes

import com.example.musicapp.data.entities.*
import com.example.musicapp.retrofit.DeezerService

class FakeDeezerService : DeezerService {
    override suspend fun searchArtists(artistName: String, limit: Int, index: Int): Search<Artist> =
        Search(listOf(), 0, "")

    override suspend fun searchTracks(trackName: String, limit: Int, index: Int): Search<Track> =
        Search(listOf(), 0, "")


    override suspend fun searchArtistsSuggestions(
        artistName: String,
        limit: Int
    ): Suggestion<SuggestArtist> = Suggestion(listOf())

    override suspend fun searchTracksSuggestions(
        artistName: String,
        limit: Int
    ): Suggestion<SuggestTrack> = Suggestion(listOf())
}