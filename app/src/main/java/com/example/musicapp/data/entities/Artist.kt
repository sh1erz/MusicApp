package com.example.musicapp.data.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL

@Entity
data class Artist(
    @PrimaryKey
    val id: Int,
    val name: String,
    val link: Uri?,
    val share: Uri?,
    val picture: Uri,
    val picture_small: Uri,
    val picture_medium: Uri,
    val picture_big: Uri,
    val picture_xl: Uri,
    val nb_album: Int?,
    val nb_fan: Int?,
    val tracklist: Uri
) : Searchable

