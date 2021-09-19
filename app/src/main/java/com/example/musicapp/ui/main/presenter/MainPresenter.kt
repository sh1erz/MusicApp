package com.example.musicapp.ui.main.presenter

import com.example.musicapp.data.MusicRepository
import com.example.musicapp.ui.main.view.IView
import javax.inject.Inject

class MainPresenter @Inject constructor(val view: IView, val repository : MusicRepository) : IPresenter{
    override fun loadTrackHistory() : Boolean {
        return true
    }

}