package com.example.musicapp.fakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.musicapp.data.db.TrackDao
import com.example.musicapp.data.entities.Track

class FakeTrackDao : TrackDao {
    val data = mutableMapOf<Long, Track>()

    override fun insertTracks(tracks: List<Track>) {
        tracks.forEach { insertTrack(it) }
    }

    override fun insertTrack(track: Track): Long {
        data[track.id] = track
        return track.id
    }

    override fun getAllTracks(): LiveData<List<Track>> = liveData { data.values }

    override fun getTrackById(id: Long): Track = data[id] ?: throw Exception("no such track")


    override fun deleteTrack(track: Track): Int = data.remove(track.id)?.id?.toInt() ?: 0

    override fun clear() = data.clear()
}

