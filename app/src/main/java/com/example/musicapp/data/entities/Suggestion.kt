package com.example.musicapp.data.entities

data class Suggestion<T : Suggestible>(
    val data: List<T>
)

interface Suggestible
data class SuggestTrack(val title: String) : Suggestible
data class SuggestArtist(val name: String) : Suggestible