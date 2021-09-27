package com.example.musicapp

import com.example.musicapp.data.db.TrackDao
import com.example.musicapp.fakes.FakeDeezerService
import com.example.musicapp.fakes.FakeTrackDao
import com.example.musicapp.fakes.TRACKS_DATASET
import com.example.musicapp.retrofit.DeezerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestRoomModule {
    @Provides
    fun provideTrackDao(): TrackDao = FakeTrackDao().apply {
        insertTracks(TRACKS_DATASET)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object TestDataSourceModule {
    @Provides
    fun provideDeezerService(): DeezerService = FakeDeezerService()
}
