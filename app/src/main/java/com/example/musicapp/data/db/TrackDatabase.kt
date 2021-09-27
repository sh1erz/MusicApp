package com.example.musicapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.musicapp.data.entities.Track

@Database(
    entities = [Track::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TrackDatabase : RoomDatabase() {
    abstract fun getTrackDao(): TrackDao
    companion object{
        val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE track ADD COLUMN newColumn INTEGER")
            }
        }
    }
}