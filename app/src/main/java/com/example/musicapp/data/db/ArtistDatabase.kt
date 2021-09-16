package com.example.musicapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.musicapp.data.db.ArtistDao
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.data.entities.Track

@Database(entities = [Artist::class, Track::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArtistDatabase : RoomDatabase() {
    abstract fun getArtistDao(): ArtistDao
}