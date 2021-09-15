package com.example.musicapp.data.db

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
    ): ArtistDatabase = Room.databaseBuilder(
        applicationContext,
        ArtistDatabase::class.java,
        "music.db"
    ).build()

    @Provides
    fun provideArtistDao(db: ArtistDatabase): ArtistDao = db.getArtistDao()
}

