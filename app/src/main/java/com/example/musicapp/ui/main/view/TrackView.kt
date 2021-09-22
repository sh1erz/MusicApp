package com.example.musicapp.ui.main.view

import android.os.Parcelable
import androidx.lifecycle.LifecycleOwner

interface TrackView {
    fun showDetails(parcelable: Parcelable)
    fun showList()
    fun getViewLifecycleOwner(): LifecycleOwner
}