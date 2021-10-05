package com.example.musicapp.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.musicapp.LOG
import com.example.musicapp.data.db.TrackDao
import com.example.musicapp.data.db.TrackDatabase
import com.example.musicapp.retrofit.DeezerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideService(): DeezerService {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                Log.i(LOG, "url: ${request.url()}")
                chain.proceed(request)
            }.build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(DeezerService::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.deezer.com/"
    }
}

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideRoom(
        @ApplicationContext applicationContext: Context
    ): TrackDatabase = Room.databaseBuilder(
        applicationContext,
        TrackDatabase::class.java,
        "music.db"
    ).build()

    @Provides
    fun provideTrackDao(db: TrackDatabase): TrackDao = db.getTrackDao()
}




