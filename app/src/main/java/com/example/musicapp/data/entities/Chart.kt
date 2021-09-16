package com.example.musicapp.data.entities

data class Chart(
    val tracks: DataTotal<Track>
)

data class DataTotal<T : Searchable>(
    val data: List<T>,
    val total: Int
)

