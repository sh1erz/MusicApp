package com.example.musicapp.ui.main.presenter

import com.example.musicapp.data.MusicRepository
import com.example.musicapp.ui.main.view.IView
import javax.inject.Inject

class MainPresenter(val view: IView) : IPresenter{
    @Inject
    lateinit var repository: MusicRepository
    override fun loadTrackHistory() : Boolean {
        return true
    }

}