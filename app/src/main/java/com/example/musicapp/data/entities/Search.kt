package com.example.musicapp.data.entities

//w
data class Search<T : Searchable>(
    val data: List<T>,
    val total: Int,
    val next: String
)
