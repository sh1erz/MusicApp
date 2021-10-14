package com.example.data.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Search<T : Searchable>(
    @SerializedName("data")
    val data: List<T>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("next")
    val next: String
)

