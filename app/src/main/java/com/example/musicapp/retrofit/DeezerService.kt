package com.example.musicapp.retrofit

import com.example.musicapp.data.entities.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerService {
    //for charts
    @GET("genre")
    fun getGenres(): Genres

    @GET("chart/{genre_id}/tracks")
    fun getTopTracksByGenre(@Path("genre_id") genreId: Int, @Query("limit") limit: Int = 25): Chart


    //search
    @GET("search/artist")
    suspend fun searchArtists(@Query("q") artistName: String): Search<Artist>

    @GET("search/track")
    suspend fun searchTracks(@Query("q") trackName: String): Search<Track>

    @GET("search/artist")
    fun searchArtistsSuggestions(
        @Query("q") artistName: String,
        @Query("limit") limit: Int = 5
    ): Call<Suggestion<SuggestArtist>>

    @GET("search/track")
    fun searchTracksSuggestions(
        @Query("q") artistName: String,
        @Query("limit") limit: Int = 5
    ): Call<Suggestion<SuggestTrack>>


}