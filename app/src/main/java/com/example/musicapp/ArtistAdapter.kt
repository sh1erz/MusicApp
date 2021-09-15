package com.example.musicapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.databinding.ArtistItemBinding
import com.squareup.picasso.Picasso

class ArtistAdapter(
    private val artists: MutableList<Artist>,
    private val listener: ListItemListener
) :
    RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    fun addArtists(artists: List<Artist>) {
        this.artists.apply {
            clear()
            addAll(artists)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        with(artists[position]) {
            holder.binding.apply {
                tvName.text = name
                Picasso.with(imgArtist.context)
                    .load(picture_medium)
                    .into(imgArtist)
                constraint.setOnClickListener {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = artists.size

    class ArtistViewHolder(val binding: ArtistItemBinding) : RecyclerView.ViewHolder(binding.root)
}

interface ListItemListener {
    fun onItemClick(position: Int)
}