package com.example.musicapp.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.musicapp.data.db.ArtistDao
import com.example.musicapp.data.db.ArtistDatabase
import com.example.musicapp.retrofit.DeezerService
import com.example.musicapp.ui.main.LOG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

