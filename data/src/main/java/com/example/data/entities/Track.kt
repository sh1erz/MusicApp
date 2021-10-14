package com.example.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Keep
@Parcelize
data class Track(
    @SerializedName("id")
    @PrimaryKey
    val id: Long,
    var listened: Long = System.currentTimeMillis(),
    @SerializedName("title")
    val title: String,
    @SerializedName("link")
    val link: String?,
    @SerializedName("share")
    val share: String?,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("track_position")
    val track_position: String?,
    @SerializedName("disk_number")
    val disk_number: Int?,
    @SerializedName("preview")
    @ColumnInfo(name = "preview") val musicUri: String, //mp3
    val contributors: List<Artist>?,
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("album")
    var album: Album,
    @SerializedName("type")
    val type: String?
) : Searchable, Parcelable



