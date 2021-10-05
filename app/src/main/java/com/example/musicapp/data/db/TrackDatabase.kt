package com.example.musicapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.musicapp.data.entities.Track

@Database(
    entities = [Track::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TrackDatabase : RoomDatabase() {
    abstract fun getTrackDao(): TrackDao
}
