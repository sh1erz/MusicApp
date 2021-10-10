package com.example.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

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