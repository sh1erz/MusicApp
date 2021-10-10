package com.example.musicapp.fakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.db.TrackDao

class FakeTrackDao : TrackDao {
    val data = mutableMapOf<Long, com.example.data.entities.Track>()

    override fun insertTracks(tracks: List<com.example.data.entities.Track>) {
        tracks.forEach { insertTrack(it) }
    }

    override fun insertTrack(track: com.example.data.entities.Track): Long {
        data[track.id] = track
        return track.id
    }

    override fun getAllTracks(): LiveData<List<com.example.data.entities.Track>> = liveData { data.values }

    override fun getTrackById(id: Long): com.example.data.entities.Track = data[id] ?: throw Exception("no such track")


    override fun deleteTrack(track: com.example.data.entities.Track): Int = data.remove(track.id)?.id?.toInt() ?: 0

    override fun clear() = data.clear()
}

