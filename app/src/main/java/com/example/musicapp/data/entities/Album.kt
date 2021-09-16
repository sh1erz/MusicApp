package com.example.musicapp.data.entities

import android.net.Uri
import java.net.URL
import java.util.*

data class Album(
    val id: Int,
    val title: String,
    val link: String?,
    val share: String?,
    val cover: String,
    val cover_small: String,
    val cover_medium: String,
    val cover_big: String,
    val cover_xl: String,
    val genre_id: String,
    val genres: List<Genre>?,
    val duration : Int?,
    val tracklist: String?,
    val tracks : List<Track>?
) :Searchable
