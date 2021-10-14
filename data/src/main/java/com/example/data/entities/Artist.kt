package com.example.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Keep
@Parcelize
data class Artist(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("link")
    val link: String?,
    @SerializedName("share")
    val share: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("picture_small")
    val picture_small: String?,
    @SerializedName("picture_medium")
    val picture_medium: String?,
    @SerializedName("picture_big")
    val picture_big: String?,
    @SerializedName("picture_xl")
    val picture_xl: String?,
    @SerializedName("nb_album")
    val nb_album: Int?,
    @SerializedName("nb_fan")
    val nb_fan: Int?,
    @SerializedName("tracklist")
    val tracklist: String
) : Searchable, Parcelable

