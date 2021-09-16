package com.example.musicapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Artist(
    @PrimaryKey
    val id: Int,
    val name: String,
    val link: String?,
    val share: String?,
    val picture: String,
    val picture_small: String,
    val picture_medium: String,
    val picture_big: String,
    val picture_xl: String,
    val nb_album: Int?,
    val nb_fan: Int?,
    val tracklist: String
) : Searchable

