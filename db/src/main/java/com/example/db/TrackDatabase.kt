package com.example.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.entities.Track

@Database(
    entities = [Track::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TrackDatabase : RoomDatabase() {
    abstract fun getTrackDao(): TrackDao
}
