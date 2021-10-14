package com.example.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Album(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("link")
    val link: String?,
    @SerializedName("share")
    val share: String?,
    @SerializedName("cover")
    var cover: String,
    @SerializedName("cover_small")
    var cover_small: String,
    @SerializedName("cover_medium")
    val cover_medium: String,
    @SerializedName("cover_big")
    val cover_big: String,
    @SerializedName("cover_xl")
    val cover_xl: String,
    @SerializedName("genre_id")
    val genre_id: String?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("tracklist")
    val tracklist: String?,
    @SerializedName("tracks")
    val tracks: List<Track>?
) : Searchable, Parcelable
