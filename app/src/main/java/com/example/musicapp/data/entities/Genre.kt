package com.example.musicapp.data.entities

import java.net.URL

data class Genre(
    val id:Int,
    val name:String,
    val picture: URL,
    val picture_small: URL,
    val picture_medium: URL,
    val picture_big: URL,
    val picture_xl: URL,
)