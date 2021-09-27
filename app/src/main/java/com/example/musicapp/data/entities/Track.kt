package com.example.musicapp.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Track(
    @PrimaryKey
    val id: Long,
    var listened: Long = System.currentTimeMillis(),
    val title: String,
    val link: String,
    val share: String?,
    val duration: Int,
    val track_position: String?,
    val disk_number: Int?,
    @SerializedName("preview")
    @ColumnInfo(name = "preview") val musicUri: String, //mp3
    val contributors: List<Artist>?,
    val artist: Artist,
    val album: Album,
    val type: String?,
    val newColumn : Int? = 0
) : Searchable, Parcelable



