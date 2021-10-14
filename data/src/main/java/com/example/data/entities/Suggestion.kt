package com.example.data.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Suggestion<T : Suggestible>(
    @SerializedName("data")
    val data: List<T>
)

interface Suggestible

@Keep
data class SuggestTrack(val title: String) : Suggestible

@Keep
data class SuggestArtist(val name: String) : Suggestible