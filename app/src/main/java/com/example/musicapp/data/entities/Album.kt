package com.example.musicapp.data.entities

import android.net.Uri
import java.net.URL
import java.util.*

data class Album(
    val id: Int,
    val title: String,
    val link: Uri?,
    val share: Uri?,
    val cover: Uri,
    val cover_small: Uri,
    val cover_medium: Uri,
    val cover_big: Uri,
    val cover_xl: Uri,
    val genre_id: Int,
    val genres: List<Genre>?,
    val duration : Int?,
    val tracklist: Uri?,
    val tracks : List<Track>?
) :Searchable
