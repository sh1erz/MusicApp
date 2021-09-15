package com.example.musicapp.data.db

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter


class Converter {
    @TypeConverter
    fun uriToString(uri: Uri?): String? = uri?.toString()

    @TypeConverter
    fun stringToUri(s: String?): Uri? = s?.let { Uri.parse(s) }


}