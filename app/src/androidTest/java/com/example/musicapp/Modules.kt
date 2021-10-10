package com.example.musicapp

import com.example.retrofit.DeezerService
import com.example.db.TrackDao
import com.example.musicapp.fakes.FakeDeezerService
import com.example.musicapp.fakes.FakeTrackDao
import com.example.musicapp.fakes.TRACKS_DATASET
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

