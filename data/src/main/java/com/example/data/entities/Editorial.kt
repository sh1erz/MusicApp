package com.example.data.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Editorial<T>(
    @SerializedName("data")
    val data: List<T>
)
