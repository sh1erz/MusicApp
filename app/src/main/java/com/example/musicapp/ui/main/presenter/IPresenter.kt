package com.example.musicapp.ui.main.presenter

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


interface IPresenter {
    fun loadTrackHistory() : Boolean
}