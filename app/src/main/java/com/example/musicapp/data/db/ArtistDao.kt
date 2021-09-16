package com.example.musicapp.data.db
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.data.entities.Track

@Dao
interface ArtistDao {

    @Insert(onConflict = IGNORE)
    fun insertArtists(artist: List<Artist>)

    @Insert(onConflict = IGNORE)
    fun insertTrack(track: Track)


    @Query("SELECT * FROM artist")
    fun getAll(): LiveData<List<Artist>>

    @Query("DELETE FROM artist")
    fun clear()
}