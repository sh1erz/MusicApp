package com.example.musicapp.data.entities

data class Track(
    val id: String,
    val title: String,
    val link: String,
    val share: String?,
    val duration: Int,
    val track_position: String,
    val disk_number: Int,
    val preview: String, //mp3!!!!
    val contributors: List<Artist>,
    val artist: Artist,
    val album: Album,
    val type: String
) : Searchable



