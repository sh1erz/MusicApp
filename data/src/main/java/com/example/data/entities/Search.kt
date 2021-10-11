package com.example.data.entities

//w
data class Search<T : Searchable>(
    val data: List<T>,
    val total: Int,
    val next: String
)

