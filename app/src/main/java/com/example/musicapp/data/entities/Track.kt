package com.example.musicapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Track(
    @PrimaryKey
    val id: Long,
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



