package com.example.retrofit

import com.example.data.entities.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DeezerService {

    @GET("search/artist")
    suspend fun searchArtists(
        @Query("q") artistName: String,
        @Query("limit") limit: Int = 25,
        @Query("index") index: Int = 0
    ): Search<Artist>

    @GET("search/track")
    suspend fun searchTracks(
        @Query("q") trackName: String,
        @Query("limit") limit: Int,
        @Query("index") index: Int
    ): Search<Track>

    @GET("search/artist")
    suspend fun searchArtistsSuggestions(
        @Query("q") artistName: String,
        @Query("limit") limit: Int
    ): Suggestion<SuggestArtist>

    @GET("search/track")
    suspend fun searchTracksSuggestions(
        @Query("q") artistName: String,
        @Query("limit") limit: Int
    ): Suggestion<SuggestTrack>

    @GET("editorial/{genre_id}/releases")
    suspend fun getReleaseAlbums(
        @Path("genre_id") genreId: Int
    ):Editorial<Album>

    @GET("album/{id}/tracks")
    suspend fun findAlbumTracks(
        @Path("id") id: Long
    ): Editorial<Track>


}