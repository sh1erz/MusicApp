package com.example.musicapp.data.entities

import java.net.URL
import java.util.*

data class Album(
    val id: Int,
    val title: String,
    val link: URL?,
    val share: URL?,
    val cover: URL,
    val cover_small: URL,
    val cover_medium: URL,
    val cover_big: URL,
    val cover_xl: URL,
    val genre_id: Int,
    val genres: List<Genre>?,
    val duration : Int?,
    val tracklist: URL?,
    val tracks : List<Track>?
) :Searchable
