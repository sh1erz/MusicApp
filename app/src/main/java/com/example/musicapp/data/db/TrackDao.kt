package com.example.musicapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.data.entities.Track

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTracks(tracks: List<Track>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTrack(track: Track)

    @Query("SELECT * FROM track")
    fun getAllTracks(): LiveData<List<Track>>

    @Query("SELECT * FROM track WHERE id=:id")
    fun getTrackById(id: Int) : Track

    @Query("DELETE FROM track")
    fun clear()
}