package com.example.musicapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicapp.data.entities.Track
import com.example.musicapp.databinding.FragmentTrackBinding
import com.squareup.picasso.Picasso


class TrackFragment : Fragment(){

    private var _binding: FragmentTrackBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //passed from recycler with history or search
        arguments?.getParcelable<Track>(TRACK)?.let { track ->
            //todo xml
            binding.apply {
                Picasso.with(context)
                    .load(track.album.cover_xl)
                    .into(imgTrack)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TRACK = "track"
    }


}