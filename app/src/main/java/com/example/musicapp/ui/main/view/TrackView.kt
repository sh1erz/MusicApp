package com.example.musicapp.ui.main.view

import android.os.Parcelable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.FragmentNavigator

interface TrackView {
    fun showDetails(parcelable: Parcelable, extras: FragmentNavigator.Extras? = null)
    fun showList()
    fun getViewLifecycleOwner(): LifecycleOwner
}