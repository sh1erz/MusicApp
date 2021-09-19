package com.example.musicapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.databinding.FragmentDetailsBinding
import com.example.musicapp.ui.adapters.OnArtistClickListener
import com.example.musicapp.ui.main.MainViewModel
import com.squareup.picasso.Picasso


class ArtistFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*arguments?.getInt("artist_position")?.let { position ->
            viewModel.getArtists().observe(viewLifecycleOwner) {
                val artist = it[position]
                binding.apply {
                    Picasso.with(context)
                        .load(artist.picture_xl)
                        .into(imgArtist)
                    tvName.text = artist.name
                    tvLink.text = artist.link.toString()
                }

            }
        }*/

    }

}