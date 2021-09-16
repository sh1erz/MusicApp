package com.example.musicapp.data.db

import androidx.room.TypeConverter
import com.example.musicapp.data.entities.Album
import com.example.musicapp.data.entities.Artist
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromArtist(artist: Artist?): String = Gson().toJson(artist)

    @TypeConverter
    fun toArtist(s: String): Artist = Gson().fromJson(s, Artist::class.java)

    @TypeConverter
    fun fromArtistList(list: List<Artist>?): String = Gson().toJson(list)

    @TypeConverter
    fun toArtistList(s: String): List<Artist> =
        Gson().fromJson(s, Array<Artist>::class.java).toList()

    @TypeConverter
    fun fromAlbum(album: Album?): String = Gson().toJson(album)

    @TypeConverter
    fun toAlbum(s: String): Album = Gson().fromJson(s, Album::class.java)

}