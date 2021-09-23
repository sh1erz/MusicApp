package com.example.musicapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.musicapp.data.entities.Track

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTracks(tracks: List<Track>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(track: Track): Long

    fun insertWithTimestamp(data: Track) {
        insertTrack(data.apply {
            listened = System.currentTimeMillis()
        })
    }

    @Query("SELECT * FROM track ORDER BY listened DESC")
    fun getAllTracks(): LiveData<List<Track>>

    @Query("SELECT * FROM track WHERE id=:id")
    fun getTrackById(id: Long): Track

    @Delete
    fun deleteTrack(track: Track): Int

    @Query("DELETE FROM track")
    fun clear()
}