package com.example.musicapp.ui.details

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.R
import com.example.musicapp.data.entities.Track
import com.example.musicapp.databinding.FragmentTrackBinding
import com.example.musicapp.ui.adapters.OnTrackClickListener


class TrackFragment : Fragment(){


    private lateinit var binding : FragmentTrackBinding
    var mediaPlayer : MediaPlayer? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}