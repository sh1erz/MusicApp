package com.example.musicapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.musicapp.data.entities.Track

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTracks(tracks: List<Track>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(track: Track)

    @Transaction
    fun insertTrackUpIfExists(track: Track) {
        deleteTrack(track.id)
        insertTrack(track)
    }

    @Query("SELECT * FROM track ORDER BY db_id DESC")
    fun getAllTracks(): LiveData<List<Track>>

    @Query("SELECT * FROM track WHERE id=:id")
    fun getTrackById(id: Long) : Track

    @Query("DELETE FROM track WHERE id=:id")
    fun deleteTrack(id: Long)

    @Query("DELETE FROM track")
    fun clear()
}