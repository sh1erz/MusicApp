package com.example.musicapp.retrofit

import com.example.musicapp.data.entities.Artist
import com.example.musicapp.data.entities.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerService {

    @GET("search/artist")
    suspend fun searchArtists(@Query("q") artistName: String): Search<Artist>

    @GET("search/artist")
    fun searchArtistsRxJava(
        @Query("q") artistName: String,
        @Query("limit") limit: Int = 5
    ): Call<Search<Artist>>

}