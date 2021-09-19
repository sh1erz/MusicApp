package com.example.musicapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.databinding.FragmentArtistBinding
import com.squareup.picasso.Picasso


class ArtistFragment : Fragment() {

    private lateinit var binding: FragmentArtistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Artist>(ARTIST)?.let { artist ->
            binding.apply {
                Picasso.with(context)
                    .load(artist.picture_xl)
                    .into(imgArtist)
                tvName.text = artist.name
                tvLink.text = artist.link.toString()
            }
        }

    }
    companion object {
        const val ARTIST = "artist"
    }

}